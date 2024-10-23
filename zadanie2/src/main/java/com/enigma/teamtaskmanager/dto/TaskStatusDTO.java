package com.enigma.teamtaskmanager.dto;

import com.enigma.teamtaskmanager.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskStatusDTO {
    @NotNull
    private TaskStatus status;
}

