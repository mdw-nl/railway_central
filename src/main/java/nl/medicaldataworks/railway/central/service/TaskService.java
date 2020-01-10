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

    public Page<Task> findTasks(Pageable pageable, CalculationStatus calculationStatus, Long stationId){
        Page<Task> page;
        if(stationId != null && calculationStatus != null){
            page = taskRepository.findByStationIdAndCalculationStatus(pageable, stationId, calculationStatus);
        } else if(stationId != null){
            page = taskRepository.findByStationId(pageable, stationId);
        } else if(calculationStatus != null) {
            page = taskRepository.findByCalculationStatus(pageable, calculationStatus);
        } else {
            page = taskRepository.findAll(pageable);
        }
        return page;
    }
}
