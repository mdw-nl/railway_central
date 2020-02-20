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
            if(unCompletedTrain.getCurrentIteration() == 0){
                continue; //never make a client task for the first iteration
            }
            Page<Task> currentTasksForTrainId = taskRepository
                    .findByTrainIdAndIteration(null, unCompletedTrain.getId(), unCompletedTrain.getCurrentIteration());
            log.trace("Tasks for uncompleted train {}: {}", unCompletedTrain, currentTasksForTrainId);
            long numberOfCurrentTasks = currentTasksForTrainId.stream().count();
            long numberOfCompletedCurrentTasks = currentTasksForTrainId.stream()
                                        .filter(task -> task.getCalculationStatus().equals(CalculationStatus.COMPLETED))
                                        .count();
            if(numberOfCurrentTasks > unCompletedTrain.getClientTaskCount()){
                continue; //If there are more tasks than client tasks, we must have already made a master task.
            }
            log.trace("client task count: {}, current completed task count: {}",
                    unCompletedTrain.getClientTaskCount(),
                    numberOfCompletedCurrentTasks);
            if(unCompletedTrain.getClientTaskCount() == numberOfCompletedCurrentTasks){
                log.debug("Starting new iteration for {}", unCompletedTrain);
                createNewMasterTask(unCompletedTrain);
                trainRepository.save(unCompletedTrain);
            }
        }
    }

    private void createNewMasterTask(Train unCompletedTrain) {
        log.debug("Starting new master task for {}", unCompletedTrain);
        Optional<Task> previousMasterTask = taskRepository
                .findByIterationAndTrainIdAndMasterTrue(unCompletedTrain.getCurrentIteration() - 1, unCompletedTrain.getId());
        Task currentMasterTask = new Task(null, new Date(), unCompletedTrain.getId(),
                CalculationStatus.REQUESTED, null, previousMasterTask.get().getStationId(), previousMasterTask.get().getResult(),
                true, unCompletedTrain.getCurrentIteration(), null, null);
        taskRepository.save(currentMasterTask);
    }
}