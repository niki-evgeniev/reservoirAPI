package rest.reservoirapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import rest.reservoirapi.models.WeeklySnapshotRow;
import rest.reservoirapi.models.dto.WeeklyReportDTO;
import rest.reservoirapi.models.entity.WeeklyReport;
import rest.reservoirapi.repository.ReservoirRepository;
import rest.reservoirapi.repository.WeeklyReportRepository;
import rest.reservoirapi.service.WeeklyReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {

    private final ReservoirRepository reservoirRepository;
    private final WeeklyReportRepository weeklyReportRepository;
    private final ModelMapper modelMapper;

    public WeeklyReportServiceImpl(ReservoirRepository reservoirRepository, WeeklyReportRepository weeklyReportRepository,
                                   ModelMapper modelMapper) {
        this.reservoirRepository = reservoirRepository;
        this.weeklyReportRepository = weeklyReportRepository;
        this.modelMapper = modelMapper;
    }

    public int collectLast12WeeksInfo(LocalDate mondayStart, List<String> names) {

        if (weeklyReportRepository.count() != 0) {
            weeklyReportRepository.deleteAll();
            System.out.println("DELETE ALL INFORMATION FROM WeeklyReportRepository");
        }
        LocalDate monday = isoMonday(mondayStart);
        int inserted = 0;

        for (int i = 0; i < 12; i++) {
            LocalDate weekMonday = monday.minusWeeks(i);
            LocalDate weekTuesday = weekMonday.plusDays(1);
            List<WeeklyReport> weeklyReportList = new ArrayList<>();

            for (String name : names) {
                if (weeklyReportRepository.existsByReservoirNameAndDate(name, weekMonday)) {
                    continue;
                }

                Optional<WeeklySnapshotRow> rowOpt = reservoirRepository.findBestOfWeek(name, weekMonday, weekTuesday);
                if (rowOpt.isEmpty()) {
                    continue;
                }

                WeeklySnapshotRow row = rowOpt.get();

                WeeklyReport weeklyReport = new WeeklyReport();
                weeklyReport.setReservoirName(name);

                LocalDate weekDate = rowOpt.get().getWeekDate();
                weeklyReport.setDate(weekDate);

                weeklyReport.setUsefulVolume(row.getVolumePercentage() == null ? null : row.getVolumePercentage().toString());
                weeklyReport.setTotalVolume(row.getFillPercentage() == null ? null : row.getFillPercentage().toString());

                weeklyReportList.add(weeklyReport);
                inserted++;
            }
            weeklyReportRepository.saveAll(weeklyReportList);
        }
        return inserted;
    }

    @Override
    public List<WeeklyReportDTO> getDiagramInfo(String name) {

        List<WeeklyReport> diagramReservoir = weeklyReportRepository.findByReservoirNameOrderByDateAsc(name);
        List<WeeklyReportDTO> diagram = diagramReservoir.stream().map(
                r -> {
                    return modelMapper.map(r, WeeklyReportDTO.class);
                }
        ).toList();
        return diagram;
    }

    private LocalDate isoMonday(LocalDate date) {
        return date.with(java.time.temporal
                .TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
    }
}
