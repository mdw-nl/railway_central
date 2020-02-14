package nl.medicaldataworks.railway.central.service;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainService {
    private TaskRepository taskRepository;
    private TrainRepository trainRepository;

    public TrainService(TaskRepository taskRepository, TrainRepository trainRepository) {
        this.taskRepository = taskRepository;
        this.trainRepository = trainRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void pollMasterTasks() {
        log.trace("Polling master tasks.");
        List<CalculationStatus> statuses = new ArrayList<>();
        statuses.add(CalculationStatus.COMPLETED);
        statuses.add(CalculationStatus.ERRORED);
        Page<Train> unCompletedTrains = trainRepository.findByCalculationStatusNotIn(null, statuses);
        log.debug("Uncompleted trains found: {}", unCompletedTrains.toList());
        for(Train unCompletedTrain : unCompletedTrains) {
            Page<Task> tasks = taskRepository
                    .findByTrainIdAndIteration(null, unCompletedTrain.getId(), unCompletedTrain.getCurrentIteration());
            log.trace("Tasks for uncompleted train {}: {}", unCompletedTrain, tasks);
            boolean failedTasks = tasks.stream().anyMatch(task -> task.getCalculationStatus().equals(CalculationStatus.ERRORED));
            if(failedTasks){
                unCompletedTrain.setCalculationStatus(CalculationStatus.ERRORED);
                trainRepository.save(unCompletedTrain);
            } else {
                long completedTasksCount = tasks.stream().filter(task -> task.getCalculationStatus().equals(CalculationStatus.COMPLETED)).count();
                log.trace("Found {} completed tasks for train: {}", completedTasksCount, unCompletedTrain);
                if(tasks.getTotalElements() > 0 && tasks.getTotalElements() == completedTasksCount){
                    startNewIteration(unCompletedTrain);
                }
            }
        }
    }

    private void startNewIteration(Train unCompletedTrain) {
        log.debug("Starting new iteration for {}", unCompletedTrain);
        createNewMasterTask(unCompletedTrain);
        unCompletedTrain.setCurrentIteration(unCompletedTrain.getCurrentIteration() + 1);
        trainRepository.save(unCompletedTrain);
    }

    private void createNewMasterTask(Train unCompletedTrain) {
        log.debug("Starting new master task for {}", unCompletedTrain);
        Optional<Task> previousMasterTask = taskRepository
                .findByIterationAndTrainIdAndMasterTrue(unCompletedTrain.getCurrentIteration(), unCompletedTrain.getId());
        Task currentMasterTask = new Task(null, new Date(), unCompletedTrain.getId(),
                CalculationStatus.REQUESTED, null, previousMasterTask.get().getStationId(), previousMasterTask.get().getResult(),
                true, unCompletedTrain.getCurrentIteration() + 1);
        taskRepository.save(currentMasterTask);
    }
}