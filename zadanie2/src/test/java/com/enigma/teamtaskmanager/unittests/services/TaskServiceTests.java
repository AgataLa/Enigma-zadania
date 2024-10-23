package com.enigma.teamtaskmanager.unittests.services;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.exception.BadRequestArgumentException;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.enigma.teamtaskmanager.service.TaskServiceImpl;
import com.enigma.teamtaskmanager.service.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void givenValidTaskCreateDTO_whenCreateTask_ReturnCreatedTaskId() {
        final TaskCreateDTO taskCreateDTO = TestDataGenerator.createTaskCreateDTOWithoutUsers();
        final Task task = TestDataGenerator.createTaskWithoutUsersA();
        final long expectedId = 1L;
        task.setId(expectedId);
        given(taskMapper.mapToTask(taskCreateDTO)).willReturn(task);
        given(taskRepository.save(any(Task.class))).willReturn(task);

        final Long result = taskService.createTask(taskCreateDTO);

        assertThat(result).isEqualTo(expectedId);
    }

    @Test
    public void givenTaskCreateDTOWithNonExistingUser_whenCreateTask_ThrowBadRequestArgumentException() {
        final Set<Long> usersIds = Set.of(100L);
        final TaskCreateDTO taskCreateDTO = TestDataGenerator.createTaskCreateDTOWithUsers(usersIds);
        given(userRepository.findAllById(usersIds)).willReturn(Collections.emptySet());

        assertThrows(BadRequestArgumentException.class, () -> taskService.createTask(taskCreateDTO));
    }
}
