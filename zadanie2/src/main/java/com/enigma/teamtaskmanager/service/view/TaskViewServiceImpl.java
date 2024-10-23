package com.enigma.teamtaskmanager.service.view;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.TaskStatus;
import com.enigma.teamtaskmanager.dto.TaskFiltersDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.specification.TaskSpecification;
import com.enigma.teamtaskmanager.service.mapper.TaskMapper;
import com.enigma.teamtaskmanager.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskViewServiceImpl implements TaskViewService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Override
    public List<TaskInfoDTO> getFilteredTasks(final TaskFiltersDTO taskFiltersDTO) {
        final Specification<Task> specification = buildSpecificationFromFilters(taskFiltersDTO);

        final List<Task> filteredTasks = taskRepository.findAll(specification);

        return filteredTasks.stream()
                .map((Task task) -> taskMapper.mapToTaskInfoDTO(task, userMapper))
                .collect(Collectors.toList());
    }

    private Specification<Task> buildSpecificationFromFilters(final TaskFiltersDTO taskFiltersDTO) {
        Specification<Task> specification = Specification.where(null);

        final String title = taskFiltersDTO.getTitle();
        if (title != null && !title.isEmpty()) {
            specification = specification.and(TaskSpecification.filterByTitle(title));
        }

        final TaskStatus status = taskFiltersDTO.getStatus();
        if (status != null) {
            specification = specification.and(TaskSpecification.filterByStatus(status));
        }

        final LocalDateTime dateFrom = taskFiltersDTO.getDateFrom();
        final LocalDateTime dateTo = taskFiltersDTO.getDateTo();
        if (dateFrom != null || dateTo != null) {
            specification = specification.and(TaskSpecification.filterByRealizationDate(dateFrom, dateTo));
        }

        final Long assignedUser = taskFiltersDTO.getAssignedUser();
        if (assignedUser != null) {
            specification = specification.and(TaskSpecification.filterByAssignedUser(assignedUser));
        }
        return specification;
    }

    @Override
    public TaskInfoDTO getTaskById(final Long taskId) {
        final Task foundTask = findTaskById(taskId);
        return taskMapper.mapToTaskInfoDTO(foundTask, userMapper);
    }

    private Task findTaskById(final Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", taskId));
    }
}
