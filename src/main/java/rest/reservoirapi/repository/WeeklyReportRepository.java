package rest.reservoirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.reservoirapi.models.entity.WeeklyReport;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {

    boolean existsByReservoirNameAndDate(String reservoirName, LocalDate date);

    List<WeeklyReport> findByReservoirNameOrderByDateAsc(String name);
}
