package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findByIdAndOwnerName(Long id, String ownerName);

    Page<Train> findByOwnerName(Pageable pageable, String name);
}
