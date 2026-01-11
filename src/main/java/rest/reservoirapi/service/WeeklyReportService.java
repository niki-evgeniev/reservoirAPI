package rest.reservoirapi.service;

import rest.reservoirapi.models.dto.WeeklyReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface WeeklyReportService {

    int collectLast12WeeksInfo(LocalDate mondayStart, List<String> names);

    List<WeeklyReportDTO> getDiagramInfo(String name);

}
