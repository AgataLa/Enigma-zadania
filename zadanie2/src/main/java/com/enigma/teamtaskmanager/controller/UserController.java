package com.enigma.teamtaskmanager.controller;

import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;
import com.enigma.teamtaskmanager.service.UserService;
import com.enigma.teamtaskmanager.service.view.UserViewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserViewService userViewService;

    public UserController(final UserService userService, final UserViewService userViewService) {
        this.userService = userService;
        this.userViewService = userViewService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable Long userId) {
        final UserInfoDTO userInfoDTO = userViewService.getUserById(userId);
        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserInfoDTO>> getUsers(@RequestParam(required = false) String firstname,
                                                      @RequestParam(required = false) String lastname,
                                                      @RequestParam(required = false) String email) {
        final List<UserInfoDTO> filteredUsers = userViewService.getFilteredUsers(firstname, lastname, email);
        return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        final Long createdUserId = userService.createUser(userCreateDTO);
        return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
