package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.util.KeycloakUtil;
import nl.medicaldataworks.railway.central.util.PaginationUtil;
import nl.medicaldataworks.railway.central.util.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private TaskRepository taskRepository;
    private KeycloakUtil keycloakUtil = new KeycloakUtil();;
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, Authentication authentication) {
        log.debug("REST request to get tasks : {}", id);
        Optional<Task> task = taskRepository.findByIdAndOwnerName(id, keycloakUtil.getPreferredUsernameFromAuthentication(authentication));
        return ResponseUtil.wrapOrNotFound(task);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(Pageable pageable, Authentication authentication) {
        log.debug("Getting tasks");
        Page<Task> page = taskRepository.findByClientId(pageable, keycloakUtil.getPreferredUsernameFromAuthentication(authentication));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}