package com.enigma.teamtaskmanager.unittests.controller;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.service.TaskService;
import com.enigma.teamtaskmanager.service.view.TaskViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {

    private final MockMvc mockMvc;

    @MockBean
    private TaskViewService taskViewService;

    @MockBean
    private TaskService taskService;


    private final ObjectMapper objectMapper;

    @Autowired
    public TaskControllerTests(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void givenTaskCreateDTOJson_whenPostTasks_thenReturnsHTTPCreatedAndTaskId() throws Exception {
        final TaskCreateDTO taskCreateDTO = TestDataGenerator.createTaskCreateDTOWithoutUsers();
        final String taskJson = objectMapper.writeValueAsString(taskCreateDTO);
        final Long expectedTaskId = 1L;

        given(taskService.createTask(any(TaskCreateDTO.class))).willReturn(expectedTaskId);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().string(String.valueOf(expectedTaskId))
        );

        verify(taskService, times(1)).createTask(any(TaskCreateDTO.class));
    }

    @Test
    public void givenTaskCreateDTOWithNoTitle_whenPostTasks_ReturnsHTTPBadRequest() throws Exception {
        final TaskCreateDTO taskCreateDTO = TestDataGenerator.createTaskCreateDTOWithoutUsers();
        taskCreateDTO.setTitle(null);
        final String taskJson = objectMapper.writeValueAsString(taskCreateDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        ).andExpectAll(
                MockMvcResultMatchers.status().isBadRequest()
        );

        verifyNoInteractions(taskService);
    }

    @Test
    public void givenNonExistingTaskId_whenGetTask_ReturnsHTTPNotFound() throws Exception {
        final Long nonExistingTaskId = 100L;
        when(taskViewService.getTaskById(nonExistingTaskId)).thenThrow(new ResourceNotFoundException("Task", nonExistingTaskId));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/{taskId}", nonExistingTaskId)
        ).andExpectAll(
                MockMvcResultMatchers.status().isNotFound()
        );

        verify(taskViewService, times(1)).getTaskById(nonExistingTaskId);
    }
}
