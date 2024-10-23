package com.enigma.teamtaskmanager.dto;

import com.enigma.teamtaskmanager.domain.TaskStatus;
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
public class TaskInfoDTO {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime realizationDate;
    private Set<UserInfoDTO> assignedUsers;
}
