package nl.medicaldataworks.railway.central.web.controller;

import nl.medicaldataworks.railway.central.IntegrationTest;
import nl.medicaldataworks.railway.central.domain.Task;
import nl.medicaldataworks.railway.central.service.TaskService;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Category(IntegrationTest.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getTask() throws Exception {
        when(taskService.findTask(1l)).thenReturn(java.util.Optional.of(new Task()));
        this.mockMvc.perform(get("/api/tasks/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void getAllTasks() throws Exception {
        Pageable pageable = Pageable.unpaged();
        List<Task> taskList = Arrays.asList(new Task(), new Task());
        when(taskService.findTasks(pageable, null, null))
                .thenReturn(new PageImpl<>(taskList));
        this.mockMvc.perform(get("/api/tasks")).andDo(print()).andExpect(status().isOk());
    }
}
