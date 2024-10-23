package com.enigma.teamtaskmanager.service.view;

import com.enigma.teamtaskmanager.dto.TaskFiltersDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;

import java.util.List;

public interface TaskViewService {
    List<TaskInfoDTO> getFilteredTasks(TaskFiltersDTO taskFiltersDTO);

    TaskInfoDTO getTaskById(Long taskId);
}
