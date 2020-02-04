package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Page<Train> findByCalculationStatusNotIn(Pageable pageable, List<CalculationStatus> statuses);

    Page<Train> findByCalculationStatusIn(Pageable pageable, List<CalculationStatus> statuses);
}
