package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/testentities/{id}")
    public ResponseEntity<Task> getTestentity(@PathVariable Long id) {
        log.debug("REST request to get Testentity : {}", id);
        Optional<Task> task = taskRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(task);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(Pageable pageable) {
        log.debug("Getting tasks");
        Page<Task> page = taskRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTestentity(@RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to save Task : {}", task);
        if (task.getId() != null) {
            throw new IllegalArgumentException("A new task cannot already have an ID");
        }
        Task result = taskRepository.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
                .body(result);
    }

    @PutMapping("/tasks")
    public ResponseEntity<Task> updateTestentity(@RequestBody Task task) throws URISyntaxException {
        log.debug("REST request to update task : {}", task);
        if (task.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        Task result = taskRepository.save(task);
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping("/testentities/{id}")
    public ResponseEntity<Void> deleteTestentity(@PathVariable Long id) {
        log.debug("REST request to delete task : {}", id);
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
