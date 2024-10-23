package com.enigma.teamtaskmanager.service;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.TaskStatus;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.dto.TaskStatusDTO;
import com.enigma.teamtaskmanager.dto.TaskUpdateDTO;
import com.enigma.teamtaskmanager.dto.UsersToAssignDTO;
import com.enigma.teamtaskmanager.exception.BadRequestArgumentException;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.enigma.teamtaskmanager.service.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;


    @Override
    public Long createTask(final TaskCreateDTO taskCreateDTO) {
        final Set<Long> assignedUsersId = taskCreateDTO.getAssignedUsers();
        final Set<User> assignedUsers = new HashSet<>();
        if (assignedUsersId != null) {
            assignedUsers.addAll(getAssignedUsers(assignedUsersId));
        }
        final Task taskToCreate = taskMapper.mapToTask(taskCreateDTO);
        taskToCreate.setAssignedUsers(assignedUsers);
        taskToCreate.setStatus(TaskStatus.TO_DO);

        return taskRepository.save(taskToCreate).getId();
    }

    @Override
    public void updateTask(final Long taskId, final TaskUpdateDTO taskUpdateDTO) {
        final Set<Long> assignedUsersId = taskUpdateDTO.getAssignedUsers();
        final Set<User> assignedUsers = getAssignedUsers(assignedUsersId);
        final Task taskToUpdate = findTaskById(taskId);

        updateTaskEntityFromTaskUpdateDTO(taskToUpdate, taskUpdateDTO, assignedUsers);

        taskRepository.save(taskToUpdate);
    }

    private void updateTaskEntityFromTaskUpdateDTO(final Task task, final TaskUpdateDTO taskUpdateDTO, final Set<User> assignedUsers) {
        task.setTitle(taskUpdateDTO.getTitle());
        task.setDescription(taskUpdateDTO.getDescription());
        task.setStatus(taskUpdateDTO.getStatus());
        task.setRealizationDate(taskUpdateDTO.getRealizationDate());
        task.setAssignedUsers(assignedUsers);
    }

    @Override
    public void deleteTask(final Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task", taskId);
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public void changeTaskStatus(final Long taskId, final TaskStatusDTO taskStatusDTO) {
        final Task task = findTaskById(taskId);
        task.setStatus(taskStatusDTO.getStatus());
        taskRepository.save(task);
    }

    @Override
    public void assignUsersToTask(final Long taskId, final UsersToAssignDTO usersToAssignDTO) {
        final Task task = findTaskById(taskId);
        final Set<Long> assignedUsersId = usersToAssignDTO.getUserIds();
        final Set<User> assignedUsers = getAssignedUsers(assignedUsersId);

        task.setAssignedUsers(assignedUsers);
        taskRepository.save(task);
    }

    private Task findTaskById(final Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", taskId));
    }

    private Set<User> getAssignedUsers(final Set<Long> assignedUsersId) {
        final Set<User> assignedUsers = userRepository.findAllById(assignedUsersId);

        if (assignedUsers.size() < assignedUsersId.size()) {
            throw new BadRequestArgumentException("One or more users not found in the database");
        }

        return assignedUsers;
    }
}
