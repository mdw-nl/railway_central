package nl.medicaldataworks.railway.central.service;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findTask(Long id){
        return taskRepository.findById(id);
    }

    public Page<Task> findTasks(Pageable pageable,
                                Optional<CalculationStatus> calculationStatus,
                                Optional<Long> stationId){
        return taskRepository.findByOptionalCalculationStatusAndOptionalStationId(pageable, calculationStatus, stationId);
    }

    public Page<Task> findTasks(Pageable pageable,
                                Long trainId,
                                Optional<CalculationStatus> calculationStatus,
                                Optional<Long> stationId,
                                Optional<Long> iteration){
        return taskRepository.findByTrainIdAndOptionalCalculationStatusAndOptionalStationIdAndOptionalIteration(pageable, trainId,
                                                                                            calculationStatus, stationId, iteration);
    }
}
