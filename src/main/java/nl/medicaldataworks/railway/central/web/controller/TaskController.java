package nl.medicaldataworks.railway.central.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.service.StationService;
import nl.medicaldataworks.railway.central.service.TaskService;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
@Api(tags = { "task-controller" })
public class TaskController {
    private TaskService taskService;
    private StationService stationService;

    public TaskController(TaskService taskService, StationService stationService) {
        this.taskService = taskService;
        this.stationService = stationService;
    }

    @GetMapping("/tasks/{id}")
    @ApiOperation(value = "Get a task by ID.")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.debug("REST request to get tasks : {}", id);
        Optional<Task> task = taskService.findTask(id);
        return ResponseUtil.wrapOrNotFound(task);
    }

    @GetMapping("/tasks")
    @ApiOperation(value = "Search tasks based on query parameters.")
    public ResponseEntity<List<Task>> getAllTasks(@ApiIgnore("Ignored because swagger ui shows the wrong params.") Pageable pageable,
                                                  @ApiParam(value = "Filter tasks on calculation status. Can be either REQUESTED, IDLE, PROCESSING, COMPLETED or ARCHIVED.")
                                                  @RequestParam(value = "calculation-status") Optional<String> calculationStatus,
                                                  @ApiParam(value = "Filter tasks on station name.")
                                                  @RequestParam(value = "station-name") Optional<String> stationName) {
        log.debug("Getting tasks");
        Optional<CalculationStatus> calculationStatusOptional = calculationStatus.map(CalculationStatus::valueOf);
        Optional<Long> stationId = stationService.getStationIdForStationName(stationName);
        Page<Task> page = taskService.findTasks(pageable, calculationStatusOptional, stationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
