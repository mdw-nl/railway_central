package nl.medicaldataworks.railway.central.web.controller;

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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class TaskController {
    private TaskService taskService;
    private StationService stationService;

    public TaskController(TaskService taskService, StationService stationService) {
        this.taskService = taskService;
        this.stationService = stationService;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        log.debug("REST request to get tasks : {}", id);
        Optional<Task> task = taskService.findTask(id);
        return ResponseUtil.wrapOrNotFound(task);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(Pageable pageable,
                                                  @RequestParam(value = "calculation-status", required = false) CalculationStatus calculationStatus,
                                                  @RequestParam(value = "station-name", required = false) String stationName) {
        log.debug("Getting tasks");
        Long stationId = null;
        if (stationName != null) stationId = stationService.getStationIdForStationName(stationName);
        Page<Task> page = taskService.findTasks(pageable, calculationStatus, stationId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
