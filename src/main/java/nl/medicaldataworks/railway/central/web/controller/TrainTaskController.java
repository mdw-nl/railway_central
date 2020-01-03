package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Station;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.StationRepository;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import nl.medicaldataworks.railway.central.web.dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private StationRepository stationRepository;
    private ModelMapper modelMapper;

    public TrainTaskController(TaskRepository taskRepository, TrainRepository trainRepository,
                               StationRepository stationRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/trains/{id}/tasks")
    public ResponseEntity<Page<Task>> getTasks(Pageable pageable, @PathVariable Long id) {
        log.debug("REST request to get tasks : {}", id);
        Page<Task> tasks = taskRepository.findByTrainId(pageable, id);
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
        if(!validTrain.getOwnerId().equals(authentication.getName())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
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
//        if(!validTrain.getOwnerId().equals(authentication.getName())){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        } TODO: Fix owner issues.
        Optional<Task> task = taskRepository.findById(id);
        Task validTask = task.orElseThrow(() -> new Exception("No valid task for supplied ID."));
        if(!validTask.getStationId().equals(taskDto.getStationId())){
            throw new Exception("Cannot update station ID of task.");
        }
        validTask.setCalculationStatus(taskDto.getCalculationStatus());
        validTask.setResult(taskDto.getResult());
        Task result = taskRepository.save(validTask);
        return ResponseEntity.ok()
                .body(result);
    }
}
