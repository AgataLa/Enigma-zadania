package com.enigma.teamtaskmanager.dto;

import com.enigma.teamtaskmanager.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskFiltersDTO {
    private String title;
    private TaskStatus status;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long assignedUser;
}
