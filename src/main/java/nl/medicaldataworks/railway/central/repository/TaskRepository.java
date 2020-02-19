package nl.medicaldataworks.railway.central.repository;

import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select data from Task data where (:calculationStatus is null or data.calculationStatus = :calculationStatus) and "
            + "(:stationId is null or data.stationId = :stationId)")
    Page<Task> findByOptionalCalculationStatusAndOptionalStationId(Pageable pageable,
                                                                   Optional<CalculationStatus> calculationStatus,
                                                                   Optional<Long> stationId);

    @Query("select data from Task data where (:trainId = data.trainId) and " +
            "(:calculationStatus is null or data.calculationStatus = :calculationStatus) and "
            + "(:stationId is null or data.stationId = :stationId)")
    Page<Task> findByTrainIdAndOptionalCalculationStatusAndOptionalStationId(Pageable pageable,
                                                                             Long trainId,
                                                                             Optional<CalculationStatus> calculationStatus,
                                                                             Optional<Long> stationId);

    Optional<Task> findByIterationAndTrainIdAndMasterTrue(Long currentIteration, Long trainId);

    Page<Task> findByTrainIdAndIteration(Pageable pageable, Long trainId, Long currentIteration);

    @Query("select data from Task data where (:trainId = data.trainId) and " +
            "(:calculationStatus is null or data.calculationStatus = :calculationStatus) and " +
            "(:iteration is null or data.iteration = :iteration) and "
            + "(:stationId is null or data.stationId = :stationId)")
    Page<Task>
    findByTrainIdAndOptionalCalculationStatusAndOptionalStationIdAndOptionalIteration(
            Pageable pageable, Long trainId, Optional<CalculationStatus> calculationStatus, Optional<Long> stationId,
            Optional<Long> iteration);

    Page<Task> findByTrainIdAndIterationAndMasterAndCalculationStatus(Pageable pageable,
                                                                      Long trainId,
                                                                      Long currentIteration,
                                                                      boolean master,
                                                                      CalculationStatus calculationStatus);
}