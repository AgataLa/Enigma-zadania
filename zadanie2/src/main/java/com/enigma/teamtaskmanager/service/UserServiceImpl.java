package com.enigma.teamtaskmanager.service;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import com.enigma.teamtaskmanager.exception.ResourceAlreadyExistsException;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.enigma.teamtaskmanager.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskRepository taskRepository;

    @Override
    public Long createUser(final UserCreateDTO userCreateDTO) {
        if (userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", userCreateDTO.getEmail());
        }
        final User userToCreate = userMapper.mapToUser(userCreateDTO);
        userRepository.save(userToCreate);
        return userToCreate.getId();
    }

    @Override
    public void deleteUser(final Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", userId);
        }
        final List<Task> tasksWithAssignedUser = taskRepository.findByAssignedUsersId(userId);
        for (Task task : tasksWithAssignedUser) {
            task.removeUser(user.get());
        }
        taskRepository.saveAll(tasksWithAssignedUser);
        userRepository.deleteById(userId);
    }
}
