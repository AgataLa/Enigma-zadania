package com.enigma.teamtaskmanager.service.view;

import com.enigma.teamtaskmanager.dto.UserInfoDTO;

import java.util.List;

public interface UserViewService {
    UserInfoDTO getUserById(Long userId);

    List<UserInfoDTO> getFilteredUsers(String firstname, String lastname, String email);
}
