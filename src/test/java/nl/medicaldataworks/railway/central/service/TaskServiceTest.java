package nl.medicaldataworks.railway.central.service;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static nl.medicaldataworks.railway.central.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @Test
    public void clientFindTasks() {
        Page<Task> tasks = taskService.findTasks(PageRequest.of(PAGE, PAGE_SIZE), null, null);
        assertEquals(NUMBER_OF_TASKS, tasks.getTotalElements());
        tasks = taskService.findTasks(PageRequest.of(PAGE, PAGE_SIZE), CALCULATION_STATUS, null);
        for(Task task : tasks){
            assertEquals(CALCULATION_STATUS, task.getCalculationStatus());
        }
        tasks = taskService.findTasks(PageRequest.of(PAGE, PAGE_SIZE), null, STATION_ID);
        for(Task task : tasks){
            assertEquals(STATION_ID, task.getStationId());
        }
        tasks = taskService.findTasks(PageRequest.of(PAGE, PAGE_SIZE), CALCULATION_STATUS, STATION_ID);
        for(Task task : tasks){
            assertEquals(CALCULATION_STATUS, task.getCalculationStatus());
            assertEquals(STATION_ID, task.getStationId());
        }

    }
}
