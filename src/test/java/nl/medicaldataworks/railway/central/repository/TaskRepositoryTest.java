package nl.medicaldataworks.railway.central.repository;

import lombok.extern.slf4j.Slf4j;
import nl.medicaldataworks.railway.central.domain.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static nl.medicaldataworks.railway.central.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void findByStationIdAndCalculationStatus() {
        Pageable pageable = PageRequest.of(PAGE, PAGE_SIZE);
        Page<Task> tasks = taskRepository.findByStationIdAndCalculationStatus(pageable, STATION_ID, CALCULATION_STATUS);
        assertEquals(NUMBER_OF_TASKS_FOR_STATION_WITH_CALCULATION_STATUS, tasks.getTotalElements());
        for(Task task : tasks){
            assertEquals(CALCULATION_STATUS, task.getCalculationStatus());
            assertEquals(STATION_ID, task.getStationId());
        }
    }
}
