package rest.reservoirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.reservoirapi.models.entity.SavedFiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SavedFileRepository extends JpaRepository<SavedFiles, Long> {

    Optional<SavedFiles> findByFileName(String fileName);

    List<SavedFiles> findTopByOrderByIdDesc();
//    Optional<SavedFiles> findTopByOrderByIdDesc();

    Optional<SavedFiles> findByAddedDate(LocalDate localDate);
}
