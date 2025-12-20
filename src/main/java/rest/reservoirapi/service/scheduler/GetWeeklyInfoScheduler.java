package rest.reservoirapi.service.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.WeeklyReportService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
public class GetWeeklyInfoScheduler {

    private final WeeklyReportService weeklyReportService;
    private final List<String> names = List.of(
            "Искър", "Бели Искър", "Среченска бара", "Христо Смирненски", "Йовковци", "Тича", "Камчия",
            "Ясна поляна", "Асеновец", "Боровица", "Студена", "Дяково", "Порой", "Ахелой", "Панчарево",
            "Ястребино", "Кула", "Рабиша", "Огоста", "Сопот", "Горни Дъбник", "Бели Лом", "Съединение",
            "Георги Трайков(Цонево)", "Жребчево", "Малко Шарково", "Домлян", "Пясъчник", "Тополница",
            "Тракиец", "Пчелина", "Александър Стамболийски", "Копринка", "Белмекен-Чаира", "Белмекен",
            "Чаира", "Голям Беглик-Широка поляна", "Широка поляна", "Беглика", "Тошков Чарк", "Батак",
            "Доспат", "Цанков камък", "Въча", "Кричим", "Кърджали", "Студен кладенец", "Ивайловград"
    );

    public GetWeeklyInfoScheduler(WeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

        @Scheduled(cron = "0 0 20 * * MON", zone = "Europe/Sofia")
//    @Scheduled(cron = "*/1 * * * * *", zone = "Europe/Sofia")
    public void getWeeklyInformation() {
//        LocalDate mondayStart = LocalDate.of(2025, 10, 13);
        LocalDate today = LocalDate.now();
        LocalDate mondayStart = today.with(DayOfWeek.MONDAY);
        System.out.println("Executing Scheduled : getWeeklyInformation");
        System.out.println("Start day is : " + mondayStart);
        long start = System.currentTimeMillis();
        int n = weeklyReportService.collectLast12WeeksInfo(mondayStart, names);
        long end  = System.currentTimeMillis();
        System.out.println("All executing reservoirs diagrams is : " + n + " from 576");
        System.out.printf("Time to executing method is %s millisecond %n", end - start);
    }
}
