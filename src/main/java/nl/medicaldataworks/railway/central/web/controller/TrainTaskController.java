package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class TrainTaskController {
    private TaskRepository taskRepository;

    public TrainTaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/train/{id}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long id, Authentication authentication) {
        log.debug("REST request to get tasks : {}", id);
        List<Task> tasks = taskRepository.findByTrainIdAndOwnerName(id, authentication.getName());
        return ResponseEntity.ok(tasks);
    }
}
