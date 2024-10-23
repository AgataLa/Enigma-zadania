package com.enigma.teamtaskmanager.unittests.controller;

import com.enigma.teamtaskmanager.TestDataGenerator;
import com.enigma.teamtaskmanager.dto.UserCreateDTO;
import com.enigma.teamtaskmanager.exception.ResourceAlreadyExistsException;
import com.enigma.teamtaskmanager.service.UserService;
import com.enigma.teamtaskmanager.service.view.UserViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    private final MockMvc mockMvc;

    @MockBean
    private UserViewService userViewService;

    @MockBean
    private UserService userService;


    private final ObjectMapper objectMapper;

    @Autowired
    public UserControllerTests(final MockMvc mockMvc, final ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void givenValidUserCreateDTO_whenCreateUser_thenReturnHTTPCreatedAndUserId() throws Exception {
        final long expectedUserId = 1L;
        final UserCreateDTO userCreateDTO = TestDataGenerator.createUserCreateDTO();
        String userJson = objectMapper.writeValueAsString(userCreateDTO);

        given(userService.createUser(any(UserCreateDTO.class))).willReturn(expectedUserId);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().string(String.valueOf(expectedUserId))
        );

        verify(userService, times(1)).createUser(any(UserCreateDTO.class));
    }

    @Test
    public void givenUserCreateDTOWithAlreadyTakenEmail_whenCreateUser_thenThrowResourceAlreadyExistsException() throws Exception {
        final UserCreateDTO userCreateDTO = TestDataGenerator.createUserCreateDTO();
        final String userJson = objectMapper.writeValueAsString(userCreateDTO);
        final Exception exception = new ResourceAlreadyExistsException("User", "email", userCreateDTO.getEmail());

        given(userService.createUser(any(UserCreateDTO.class))).willThrow(exception);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.content().string(exception.getMessage())
        );
    }
}
