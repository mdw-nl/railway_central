package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import nl.medicaldataworks.railway.central.service.StationService;
import nl.medicaldataworks.railway.central.service.TaskService;
import nl.medicaldataworks.railway.central.web.dto.TaskDto;
import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class TrainTaskController {
    private TaskRepository taskRepository;
    private TrainRepository trainRepository;
    private StationService stationService;
    private StationRepository stationRepository;
    private ModelMapper modelMapper;
    private TaskService taskService;

    public TrainTaskController(TaskRepository taskRepository, TrainRepository trainRepository,
                               StationService stationService, StationRepository stationRepository,
                               ModelMapper modelMapper, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.trainRepository = trainRepository;
        this.stationService = stationService;
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
        this.taskService = taskService;
    }

    @GetMapping("/trains/{id}/tasks")
    public ResponseEntity<Page<Task>> getTasks(Pageable pageable, @PathVariable Long id,
                                               @RequestParam(value = "calculation-status") Optional<String> calculationStatus,
                                               @RequestParam(value = "station-name") Optional<String> stationName) {
        log.debug("REST request to get tasks : {}", id);
        Optional<CalculationStatus> calculationStatusOptional = calculationStatus.map(CalculationStatus::valueOf);
        Optional<Long> stationId = stationService.getStationIdForStationName(stationName);
        Page<Task> tasks = taskService.findTasks(pageable, calculationStatusOptional, stationId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("trains/{id}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable Long id, @RequestBody TaskDto taskDto,
                                           Authentication authentication) throws Exception {
        log.debug("REST request to save Task : {}", taskDto);
        if (taskDto.getId() != null) {
            throw new Exception("A new task cannot already have an ID");
        }
        Task task = modelMapper.map(taskDto, Task.class);
        Optional<Train> train = trainRepository.findById(id);
        Optional<Station> station = stationRepository.findById(taskDto.getStationId());
        Train validTrain = train.orElseThrow(() -> new Exception("No valid train for supplied ID."));
        Station validStation = station.orElseThrow(() -> new Exception("No valid station for supplied ID."));
//        if(!validTrain.getOwnerId().equals(authentication.getName())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        } // TODO idea is good, but problematic when creating tasks from master algorithm, fix later
        task.setTrainId(validTrain.getId());
        task.setStationId(validStation.getId());
        Task result = taskRepository.save(task);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("trains/{id}/tasks")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto, Authentication authentication)
            throws Exception {
        log.debug("REST request to update task : {}", taskDto);
        if (taskDto.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        Optional<Train> train = trainRepository.findById(id);
        Train validTrain = train.orElseThrow(() -> new Exception("No valid train for supplied ID."));
        boolean userIsOwner = validTrain.getOwnerId().equals(authentication.getName());

        Optional<Task> task = taskRepository.findById(taskDto.getId());
        Task validTask = task.orElseThrow(() -> new Exception("No valid task for supplied ID."));
        if(!validTask.getStationId().equals(taskDto.getStationId())){
            throw new Exception("Cannot update station ID of task.");
        }

        Optional<Station> station = stationRepository.findById(taskDto.getStationId());

        boolean userIsPerformingStation = false;
        if(station.isPresent() && authentication.getAuthorities().contains(new KeycloakRole(station.get().getName()))){
            userIsPerformingStation = true;
        }

        // if(!userIsOwner && !userIsPerformingStation){
        //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        // }
        validTask.setCalculationStatus(taskDto.getCalculationStatus());
        validTask.setResult(taskDto.getResult());
        Task result = taskRepository.save(validTask);
        return ResponseEntity.ok()
                .body(result);
    }
}
