package rest.reservoirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.reservoirapi.models.entity.Reservoir;

@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, Long> {
}
