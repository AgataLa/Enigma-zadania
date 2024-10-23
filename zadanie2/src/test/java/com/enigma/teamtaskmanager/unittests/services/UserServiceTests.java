package com.enigma.teamtaskmanager.unittests.services;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import com.enigma.teamtaskmanager.exception.ResourceAlreadyExistsException;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.enigma.teamtaskmanager.service.UserServiceImpl;
import com.enigma.teamtaskmanager.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void givenValidUserCreateDTO_whenCreateUser_thenUserIsSavedInDB() {
        final UserCreateDTO userCreateDTO = TestDataGenerator.createUserCreateDTO();
        given(userRepository.findByEmail(userCreateDTO.getEmail())).willReturn(Optional.empty());
        given(userMapper.mapToUser(userCreateDTO)).willReturn(TestDataGenerator.createUserA());
        userService.createUser(userCreateDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test()
    public void givenAlreadyExistingEmail_whenCreateUser_thenThrowResourceAlreadyExistingException() {
        final UserCreateDTO userCreateDTO = TestDataGenerator.createUserCreateDTO();
        final User user = TestDataGenerator.createUserA();
        given(userRepository.findByEmail(userCreateDTO.getEmail())).willReturn(Optional.of(user));

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.createUser(userCreateDTO));
    }

    @Test
    public void givenExistingUserId_whenDeleteUser_thenUserIsDeleted() {
        final long userId = 1L;
        final User user = TestDataGenerator.createUserA();
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(taskRepository.findByAssignedUsersId(userId)).willReturn(new ArrayList<>());
        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void givenNonExistingUserId_whenDeleteUser_thenThrowsResourceNotFoundException() {
        final long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
    }
}
