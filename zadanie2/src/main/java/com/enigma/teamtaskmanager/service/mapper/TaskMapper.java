package com.enigma.teamtaskmanager.service.mapper;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public Task mapToTask(final TaskCreateDTO taskCreateDTO) {
        return Task.builder()
                .title(taskCreateDTO.getTitle())
                .description(taskCreateDTO.getDescription())
                .build();
    }

    public TaskInfoDTO mapToTaskInfoDTO(final Task task, final UserMapper userMapper) {
        final Set<UserInfoDTO> assignedUsers = task.getAssignedUsers()
                .stream()
                .map(userMapper::mapToUserInfoDTO)
                .collect(Collectors.toSet());

        return TaskInfoDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .realizationDate(task.getRealizationDate())
                .assignedUsers(assignedUsers)
                .build();
    }
}