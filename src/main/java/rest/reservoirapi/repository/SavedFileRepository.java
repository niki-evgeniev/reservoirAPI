package rest.reservoirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.reservoirapi.models.entity.SavedFiles;

import java.util.Optional;

public interface SavedFileRepository extends JpaRepository<SavedFiles, Long> {

    Optional<SavedFiles> findByFileName(String fileName);
}
