package com.enigma.teamtaskmanager.unittests.services;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.dto.TaskFiltersDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.service.mapper.TaskMapper;
import com.enigma.teamtaskmanager.service.mapper.UserMapper;
import com.enigma.teamtaskmanager.service.view.TaskViewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskViewServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private TaskViewServiceImpl taskViewService;

    private Task taskA;

    @BeforeEach
    public void setUp() {
        taskA = TestDataGenerator.createTaskWithoutUsersA();
    }

    @Test
    public void givenExistingTaskId_whenGetTaskById_thenReturnTaskInfoDTO() {
        final TaskInfoDTO expected = TestDataGenerator.createTaskInfoDTOA();
        given(taskRepository.findById(taskA.getId())).willReturn(Optional.of(taskA));
        given(taskMapper.mapToTaskInfoDTO(taskA, userMapper)).willReturn(expected);

        final TaskInfoDTO result = taskViewService.getTaskById(taskA.getId());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void givenNotExistingTaskId_whenGetTaskById_thenThrowResourceNotFoundException() {
        final long nonExistingTaskId = 100L;

        final Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> taskViewService.getTaskById(nonExistingTaskId));

        final String expectedMessage = "Task with id = " + nonExistingTaskId + " not found";
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    public void givenNoFilters_whenGetFilteredUsers_thenUserSpecNotCalled() {
        final TaskFiltersDTO taskFiltersDTO = TaskFiltersDTO.builder().build();
        final List<TaskInfoDTO> result = taskViewService.getFilteredTasks(taskFiltersDTO);

        verify(taskRepository, times(1)).findAll(Specification.where(null));
    }
}
