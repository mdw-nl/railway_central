package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTrainIdAndCalculationStatusIn(Pageable pageable, Long id, List<CalculationStatus> statuses);
    Page<Task> findByCalculationStatusAndMasterTrue(Pageable pageable, CalculationStatus calculationStatus);
    Page<Task> findByTrainIdAndCalculationStatus(Pageable pageable, Long trainId, CalculationStatus completed);

    @Query("select data from Task data where (:calculationStatus is null or data.calculationStatus = :calculationStatus) and "
            + "(:stationId is null or data.stationId = :stationId)")
    Page<Task> findByOptionalCalculationStatusAndOptionalStationId(Pageable pageable, Optional<CalculationStatus> calculationStatus, Optional<Long> stationId);
}