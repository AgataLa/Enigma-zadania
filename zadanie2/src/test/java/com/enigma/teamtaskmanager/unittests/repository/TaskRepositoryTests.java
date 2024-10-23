package com.enigma.teamtaskmanager.unittests.repository;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaskRepositoryTests {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskRepositoryTests(final TaskRepository taskRepository, final UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void givenTaskWithAssignedUser_whenTaskDeleteById_thenTaskIsDeletedAndUserStillExist() {
        //given
        final User user = TestDataGenerator.createUserA();
        userRepository.save(user);

        final Set<User> usersToAssign = new HashSet<>();
        usersToAssign.add(user);

        final Task task = TestDataGenerator.createTaskWithUsers(usersToAssign);
        taskRepository.save(task);
        final Long taskId = task.getId();

        //when
        taskRepository.deleteById(taskId);

        //then
        final Optional<User> resultUser = userRepository.findById(user.getId());
        final Optional<Task> resultTask = taskRepository.findById(taskId);

        assertThat(resultTask).isEmpty();
        assertThat(resultUser).isPresent();
    }
}
