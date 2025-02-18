package rest.reservoirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.reservoirapi.models.entity.Reservoir;

import java.util.Optional;

@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, Long> {

    Optional<Reservoir> findByName(String name);

    Optional<Reservoir> findTopByNameOrderByIdDesc(String name);
}
