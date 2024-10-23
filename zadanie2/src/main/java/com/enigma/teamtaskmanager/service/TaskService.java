package com.enigma.teamtaskmanager.service;

import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.dto.TaskStatusDTO;
import com.enigma.teamtaskmanager.dto.TaskUpdateDTO;
import com.enigma.teamtaskmanager.dto.UsersToAssignDTO;
import jakarta.validation.Valid;

public interface TaskService {
    Long createTask(@Valid TaskCreateDTO taskCreateDTO);

    void updateTask(Long taskId, @Valid TaskUpdateDTO taskUpdateDTO);

    void deleteTask(Long taskId);

    void changeTaskStatus(Long taskId, TaskStatusDTO taskStatusDTO);

    void assignUsersToTask(Long taskId, UsersToAssignDTO usersToAssignDTO);
}
