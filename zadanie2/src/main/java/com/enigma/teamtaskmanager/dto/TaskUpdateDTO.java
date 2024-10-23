package com.enigma.teamtaskmanager.dto;

import com.enigma.teamtaskmanager.domain.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskUpdateDTO {
    @NotBlank
    private String title;
    private String description;
    private TaskStatus status;
    @Past
    private LocalDateTime realizationDate;
    private Set<Long> assignedUsers;
}
