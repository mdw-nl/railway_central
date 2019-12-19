package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import nl.medicaldataworks.railway.central.util.KeycloakUtil;
import nl.medicaldataworks.railway.central.web.dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class TrainTaskController {
    private TaskRepository taskRepository;
    private TrainRepository trainRepository;
    private StationRepository stationRepository;
    private ModelMapper modelMapper;
    private KeycloakUtil keycloakUtil = new KeycloakUtil();;

    public TrainTaskController(TaskRepository taskRepository, TrainRepository trainRepository,
                               StationRepository stationRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/trains/{id}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long id, Authentication authentication) {
        log.debug("REST request to get tasks : {}", id);
        //TODO if not service account
        //List<Task> tasks = taskRepository.findByTrainIdAndOwnerName(id, keycloakUtil.getPreferredUsernameFromAuthentication(authentication));
        //else
        List<Task> tasks = taskRepository.findByTrainId(id);
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
        Optional<Station> station = stationRepository.findById(taskDto.getStation());
        Train validTrain = train.orElseThrow(() -> new Exception("No valid train for supplied ID."));
        Station validStation = station.orElseThrow(() -> new Exception("No valid station for supplied ID."));
//        if(keycloakUtil.getPreferredUsernameFromAuthentication(authentication) != null && keycloakUtil.getPreferredUsernameFromAuthentication(authentication).equals(validTrain.getOwnerName())) {
        task.setOwnerName(validTrain.getOwnerName());
        task.setTrain(validTrain);
        task.setStation(validStation);
        Task result = taskRepository.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId())).build();
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
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
//        if(keycloakUtil.getPreferredUsernameFromAuthentication(authentication) != null && keycloakUtil.getPreferredUsernameFromAuthentication(authentication).equals(validTrain.getOwnerName())) {
            Optional<Task> task = taskRepository.findById(id);
            Task validTask = task.orElseThrow(() -> new Exception("No valid task for supplied ID."));
            if(!validTask.getStation().getId().equals(taskDto.getStation())){
                throw new Exception("Cannot update station ID of task.");
            }
            validTask.setCalculationStatus(taskDto.getCalculationStatus());
            validTask.setResult(taskDto.getResult());
            Task result = taskRepository.save(validTask);
            return ResponseEntity.ok()
                    .body(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
    }
}
