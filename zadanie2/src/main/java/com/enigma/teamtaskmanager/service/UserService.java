package com.enigma.teamtaskmanager.service;

import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import jakarta.validation.Valid;

public interface UserService {
    Long createUser(@Valid UserCreateDTO userCreateDTO);

    void deleteUser(Long userId);
}
