package com.enigma.teamtaskmanager;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.TaskStatus;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;
import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;

import java.util.HashSet;
import java.util.Set;

public class TestDataGenerator {

    public static Task createTaskWithoutUsersA() {
        return Task.builder()
                .id(1L)
                .title("Task A")
                .description("Description for Task A")
                .build();
    }

    public static TaskInfoDTO createTaskInfoDTOA() {
        return TaskInfoDTO.builder()
                .id(1L)
                .title("Task A")
                .description("Description for Task A")
                .status(TaskStatus.TO_DO)
                .assignedUsers(Set.of())
                .build();
    }

    public static TaskCreateDTO createTaskCreateDTOWithoutUsers() {
        return TaskCreateDTO.builder()
                .title("Test task A")
                .description("Test task description A")
                .build();
    }

    public static Task createTaskWithUsers(final Set<User> users) {
        return Task.builder()
                .title("Test task B")
                .description("Test task description B")
                .assignedUsers(users)
                .build();
    }

    public static User createUserA() {
        return User.builder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .email("jankowalski@example.com")
                .build();
    }

    public static UserInfoDTO createUserInfoDTO(final User user) {
        return UserInfoDTO.builder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .email("jankowalski@example.com")
                .build();
    }

    public static UserCreateDTO createUserCreateDTO() {
        return UserCreateDTO.builder()
                .firstname("Jan")
                .lastname("Kowalski")
                .email("jankowalski@gmail.com")
                .build();
    }

    public static User createUserB() {
        return User.builder()
                .id(2L)
                .firstname("Anna")
                .lastname("Nowak")
                .email("annanowak@gmail.com")
                .build();
    }

    public static User createUserC() {
        return User.builder()
                .id(3L)
                .firstname("Tomasz")
                .lastname("Kwiatkowski")
                .email("tkwiat@mail.pl")
                .build();
    }

    public static TaskCreateDTO createTaskCreateDTOWithUsers(final Set<Long> usersIds) {
        return TaskCreateDTO.builder()
                .title("Test title")
                .description("Test description")
                .assignedUsers(usersIds)
                .build();
    }

    public static TaskCreateDTO createTaskCreateDTOWithNonExistingUser() {
        final Set<Long> usersIds = new HashSet<>();
        usersIds.add(1L);
        return TaskCreateDTO.builder()
                .title("Test title")
                .description("Test description")
                .assignedUsers(usersIds)
                .build();
    }
}
