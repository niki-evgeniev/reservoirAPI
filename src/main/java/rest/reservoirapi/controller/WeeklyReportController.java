package rest.reservoirapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.reservoirapi.models.dto.WeeklyReportDTO;
import rest.reservoirapi.service.WeeklyReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;
    List<String> nameOfReservoir = List.of(
            "Искър", "Бели Искър", "Среченска бара", "Христо Смирненски", "Йовковци", "Тича", "Камчия",
            "Ясна поляна", "Асеновец", "Боровица", "Студена", "Дяково", "Порой", "Ахелой", "Панчарево",
            "Ястребино", "Кула", "Рабиша", "Огоста", "Сопот", "Горни Дъбник", "Бели Лом", "Съединение",
            "Георги Трайков(Цонево)", "Жребчево", "Малко Шарково", "Домлян", "Пясъчник", "Тополница",
            "Тракиец", "Пчелина", "Александър Стамболийски", "Копринка", "Белмекен-Чаира", "Белмекен",
            "Чаира", "Голям Беглик-Широка поляна", "Широка поляна", "Беглика", "Тошков Чарк", "Батак",
            "Доспат", "Цанков камък", "Въча", "Кричим", "Кърджали", "Студен кладенец", "Ивайловград", "Голям Беглик"
    );

    public WeeklyReportController(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @PostMapping("/backfill")
    public String backfill() {
        LocalDate mondayStart = LocalDate.of(2025, 10, 13);
        int n = weeklyReportService.collectLast12WeeksInfo(mondayStart, nameOfReservoir);
        return "OK, inserted=" + n;
    }

    @GetMapping("/getDiagramInfo/{name}")
    public ResponseEntity<?> getInfo(@PathVariable("name") String name) {
        List<WeeklyReportDTO> diagramInfo = weeklyReportService.getDiagramInfo(name);
        return ResponseEntity.ok(diagramInfo);
    }
}
