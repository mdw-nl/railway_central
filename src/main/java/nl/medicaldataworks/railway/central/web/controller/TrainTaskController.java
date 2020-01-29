package nl.medicaldataworks.railway.central.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.web.dto.TaskDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = { "train-task-controller" })
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
    @ApiOperation(value="Search tasks based on query parameters for the train with the supplied ID.")
    public ResponseEntity<List<Task>> getTasks(@ApiIgnore("Ignored because swagger ui shows the wrong params.") Pageable pageable, @PathVariable Long id,
                                               @ApiParam(value = "Filter tasks on calculation status. Can be either REQUESTED, IDLE, PROCESSING, COMPLETED or ARCHIVED.")
                                               @RequestParam(value = "calculation-status") Optional<String> calculationStatus,
                                               @ApiParam(value = "Filter tasks on station name.")
                                               @RequestParam(value = "station-name") Optional<String> stationName,
                                               @ApiParam(value = "Filter tasks on the iteration for which they were created.")
                                               @RequestParam(value = "iteration") Optional<Long> iteration) {
        log.debug("REST request to get tasks : {}", id);
        Optional<CalculationStatus> calculationStatusOptional = calculationStatus.map(CalculationStatus::valueOf);
        Optional<Long> stationId = stationService.getStationIdForStationName(stationName);
        Page<Task> tasks = taskService.findTasks(pageable, id, calculationStatusOptional, stationId, iteration);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), tasks);
        return ResponseEntity.ok().headers(headers).body(tasks.getContent());
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
        train.orElseThrow(() -> new Exception("No valid train for supplied ID."));

        Optional<Task> task = taskRepository.findById(taskDto.getId());
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
