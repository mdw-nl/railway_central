package nl.medicaldataworks.railway.central.web.controller;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.domain.Train;
import nl.medicaldataworks.railway.central.repository.TaskRepository;
import nl.medicaldataworks.railway.central.repository.TrainRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Transactional
public class TrainTaskController {
    private TaskRepository taskRepository;
    private TrainRepository trainRepository;

    public TrainTaskController(TaskRepository taskRepository, TrainRepository trainRepository) {
        this.taskRepository = taskRepository;
        this.trainRepository = trainRepository;
    }

    @GetMapping("/train/{id}/tasks")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long id, Authentication authentication) {
        log.debug("REST request to get tasks : {}", id);
        List<Task> tasks = taskRepository.findByTrainIdAndOwnerName(id, authentication.getName());
        return ResponseEntity.ok(tasks);
    }

//    @PostMapping("train/{id}/tasks")
//    public ResponseEntity<Task> createTask(@PathVariable Long id, @RequestBody Task task,
//                                           Authentication authentication) throws Exception {
//        log.debug("REST request to save Task : {}", task);
//        if (task.getId() != null) {
//            throw new Exception("A new task cannot already have an ID");
//        }
//        Optional<Train> train = trainRepository.findById(id);
//        Train validTrain = train.orElseThrow(() -> new Exception("No valid train for supplied ID."));
//        if(authentication.getName() != null && authentication.getName().equals(validTrain.getOwnerName())) {
//            validTrain.getTasks().add(task);
//            Train result = trainRepository.save(validTrain);
//            return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
//                    .body(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }

    @PutMapping("train/{id}/tasks")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task, Authentication authentication)
            throws Exception {
        log.debug("REST request to update task : {}", task);
        if (task.getId() == null) {
            throw new IllegalArgumentException("Invalid id");
        }
        Optional<Train> train = trainRepository.findById(id);
        Train validTrain = train.orElseThrow(() -> new Exception("No valid train for supplied ID."));
        if(authentication.getName() != null && authentication.getName().equals(validTrain.getOwnerName())) {
            Task result = taskRepository.save(task);
            return ResponseEntity.ok()
                    .body(result);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

//    @DeleteMapping("train/{id}/tasks/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication authentication) {
//        log.debug("REST request to delete task : {}", id);
//        Optional<Task> task = taskRepository.findById(id);
//        if(authentication.getName() != null && task.isPresent() &&
//                authentication.getName().equals(task.get().getOwnerName())){
//            taskRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }
}
