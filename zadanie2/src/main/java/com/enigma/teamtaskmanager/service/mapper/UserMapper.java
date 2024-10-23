package com.enigma.teamtaskmanager.service.mapper;

import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(final UserCreateDTO userCreateDTO) {
        return User.builder()
                .firstname(userCreateDTO.getFirstname())
                .lastname(userCreateDTO.getLastname())
                .email(userCreateDTO.getEmail())
                .build();
    }

    public UserInfoDTO mapToUserInfoDTO(final User user) {
        return UserInfoDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .build();
    }
}
