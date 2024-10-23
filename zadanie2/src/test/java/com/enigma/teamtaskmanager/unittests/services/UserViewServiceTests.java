package com.enigma.teamtaskmanager.unittests.services;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;
import com.enigma.teamtaskmanager.exception.ResourceNotFoundException;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.enigma.teamtaskmanager.service.mapper.UserMapper;
import com.enigma.teamtaskmanager.service.view.UserViewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserViewServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserViewServiceImpl userViewService;

    private User userA;

    @BeforeEach
    public void setUp() {
        userA = TestDataGenerator.createUserA();
    }

    @Test
    public void givenExistingUserId_whenGetUserById_thenReturnUserInfoDTO() {
        final UserInfoDTO expected = TestDataGenerator.createUserInfoDTO(userA);
        given(userRepository.findById(userA.getId())).willReturn(Optional.of(userA));
        given(userMapper.mapToUserInfoDTO(userA)).willReturn(expected);

        final UserInfoDTO result = userViewService.getUserById(userA.getId());

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void givenNotExistingUserId_whenGetUserById_thenThrowResourceNotFoundException() {
        final long nonExistingUserId = 100L;

        final Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> userViewService.getUserById(nonExistingUserId));

        final String expectedMessage = "User with id = " + nonExistingUserId + " not found";
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    public void givenNoFilters_whenGetFilteredUsers_thenUserSpecNotCalled() {
        final List<UserInfoDTO> result = userViewService.getFilteredUsers("", "", "");

        verify(userRepository, times(1)).findAll(Specification.where(null));
    }
}
