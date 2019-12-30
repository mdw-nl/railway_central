package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStationId(Pageable pageable, Long id);
    Page<Task> findByTrainId(Pageable pageable, Long id);
    Page<Task> findByStationIdAndCalculationStatus(Pageable pageable, Long stationId, CalculationStatus calculationStatus);
    Page<Task> findByCalculationStatus(Pageable pageable, CalculationStatus calculationStatus);
}