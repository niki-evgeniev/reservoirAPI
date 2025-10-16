package rest.reservoirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rest.reservoirapi.models.WeeklySnapshotRow;
import rest.reservoirapi.models.entity.Reservoir;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, Long> {

    Optional<Reservoir> findByName(String name);

    Optional<Reservoir> findTopByNameOrderByIdDesc(String name);

    @Query(value = """
            SELECT
              r.name                AS name,
              r.added_date          AS week_date,
              r.volume_percentage   AS volume_percentage,
              r.fill_percentage     AS fill_percentage
            FROM reservoir r
            WHERE r.name IN (:names)
              AND r.added_date BETWEEN :start AND :end
              AND DAYOFWEEK(r.added_date) = 2  -- 2 = Monday
            ORDER BY r.name, r.added_date
            """, nativeQuery = true)
    List<WeeklySnapshotRow> findMondaySnapshots(
            @Param("names") List<String> names,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );


    @Query(value = """
        SELECT
          r.name                AS name,
          r.added_date          AS week_date,
          r.volume_percentage   AS volume_percentage,
          r.fill_percentage     AS fill_percentage
        FROM reservoir r
        WHERE r.name = :name
          AND r.added_date BETWEEN :monday AND :tuesday
        ORDER BY
          CASE DAYOFWEEK(r.added_date)
            WHEN 2 THEN 0   -- Monday 
            WHEN 3 THEN 1   -- Tuesday 
            WHEN 4 THEN 2   -- Wednesday 
            WHEN 5 THEN 3   -- Thursday 
            ELSE 99
          END,
          r.added_date ASC
        LIMIT 1
        """, nativeQuery = true)
    Optional<WeeklySnapshotRow> findBestOfWeek(
            @Param("name") String name,
            @Param("monday") LocalDate monday,
            @Param("tuesday") LocalDate tuesday
    );
}
