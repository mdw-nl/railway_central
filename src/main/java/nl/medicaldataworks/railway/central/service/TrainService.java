package nl.medicaldataworks.railway.central.service;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TrainService {
    private TaskRepository taskRepository;

    public TrainService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void pollMasterTasks() {
        Page<Task> idleMasterTasks = taskRepository.findByCalculationStatusAndMasterTrue(null, CalculationStatus.IDLE);
        idleMasterTasks.stream().forEach(this::setToRequestIfTasksCompleted);
    }

    private void setToRequestIfTasksCompleted(Task task) {
        Long trainId = task.getTrainId();
        List<CalculationStatus> statuses = new ArrayList<>();
        statuses.add(CalculationStatus.IDLE);
        statuses.add(CalculationStatus.REQUESTED);
        statuses.add(CalculationStatus.PROCESSING);
        Page<Task> unCompletedTasks = taskRepository.findByTrainIdAndCalculationStatusIn(null, trainId, statuses);
        if(unCompletedTasks.isEmpty()){
            task.setCalculationStatus(CalculationStatus.REQUESTED);
        }
    }
}