package com.enigma.teamtaskmanager.integrationtests;

import com.enigma.teamtaskmanager.domain.Task;
import com.enigma.teamtaskmanager.domain.TaskStatus;
import com.enigma.teamtaskmanager.domain.User;
import com.enigma.teamtaskmanager.dto.TaskFiltersDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;
import com.enigma.teamtaskmanager.dto.UserInfoDTO;
import com.enigma.teamtaskmanager.dto.UsersToAssignDTO;
import com.enigma.teamtaskmanager.repository.TaskRepository;
import com.enigma.teamtaskmanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ControllersTests(final MockMvc mockMvc, final ObjectMapper objectMapper,
                            final UserRepository userRepository, final TaskRepository taskRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @BeforeEach
    public void setUp() {
        final User user1 = User.builder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .email("jankowalski@example.com")
                .build();
        final User user2 = User.builder()
                .id(2L)
                .firstname("Anna")
                .lastname("Nowak")
                .email("annanowak@example.com")
                .build();
        final User user3 = User.builder()
                .id(3L)
                .firstname("Tomasz")
                .lastname("Kwiatkowski")
                .email("tomaszkwiatkowski@example.com")
                .build();
        userRepository.saveAll(List.of(user1, user2, user3));

        final Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description for Task 1")
                .status(TaskStatus.TO_DO)
                .build();

        final Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Description for Task 2")
                .status(TaskStatus.TO_DO)
                .assignedUsers(Set.of(user1, user2))
                .build();

        final Task task3 = Task.builder()
                .id(3L)
                .title("Task 3")
                .description("Description for Task 3")
                .status(TaskStatus.TO_DO)
                .assignedUsers(Set.of(user1))
                .build();
        taskRepository.saveAll(List.of(task1, task2, task3));
    }

    @Test
    public void givenNoFilters_whenGetFilteredTasks_thenReturnHttpOKAndThreeTasks() throws Exception {
        final List<TaskInfoDTO> expectedTasks = createListOfTaskInfoDTOs();
        final TaskFiltersDTO taskFiltersDTO = TaskFiltersDTO.builder().build();
        final String filtersJson = objectMapper.writeValueAsString(taskFiltersDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filtersJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedTasks.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(expectedTasks.get(0).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(expectedTasks.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(expectedTasks.get(0).getStatus().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].assignedUsers").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expectedTasks.get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(expectedTasks.get(1).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(expectedTasks.get(1).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value(expectedTasks.get(1).getStatus().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].assignedUsers", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(expectedTasks.get(2).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value(expectedTasks.get(2).getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].description").value(expectedTasks.get(2).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].status").value(expectedTasks.get(2).getStatus().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].assignedUsers", hasSize(1)));
    }

    @Test
    public void givenTitleFilter_whenGetFilteredTasks_thenReturnHttpOKAndOneTask() throws Exception {
        final String titleFilter = "Task 2";
        final TaskFiltersDTO taskFiltersDTO = TaskFiltersDTO.builder()
                .title(titleFilter)
                .build();
        final String filtersJson = objectMapper.writeValueAsString(taskFiltersDTO);
        final List<TaskInfoDTO> tasksList = createListOfTaskInfoDTOs();
        List<TaskInfoDTO> expectedTasks = tasksList.stream().filter(t -> t.getTitle().equals(titleFilter)).toList();

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filtersJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expectedTasks.getFirst().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(expectedTasks.getFirst().getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(expectedTasks.getFirst().getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(expectedTasks.getFirst().getStatus().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].realizationDate").value(expectedTasks.getFirst().getRealizationDate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].assignedUsers", hasSize(2)));
    }

    @Test
    public void givenExistingUserId_whenAssignUserToTask_thenReturnHttpStatusNoContent() throws Exception {
        final Set<Long> usersIds = Set.of(1L);
        final UsersToAssignDTO usersToAssignDTO = UsersToAssignDTO.builder()
                .userIds(usersIds)
                .build();
        final String usersIdsJson = objectMapper.writeValueAsString(usersToAssignDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usersIdsJson))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void givenNonExistingUserId_whenAssignUserToTask_thenReturnHttpStatusBadRequest() throws Exception {
        final Set<Long> usersIds = Set.of(10L);
        final UsersToAssignDTO usersToAssignDTO = UsersToAssignDTO.builder()
                .userIds(usersIds)
                .build();
        final String usersIdsJson = objectMapper.writeValueAsString(usersToAssignDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usersIdsJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenUserId_whenDeleteUser_thenIsNotAssignedToTask() throws Exception {
        final Long userId = 2L;
        final Long taskId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{id}", taskId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignedUsers").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignedUsers.length()").value(2));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{id}", taskId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignedUsers").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignedUsers.length()").value(1));
    }

    private List<TaskInfoDTO> createListOfTaskInfoDTOs() {
        final UserInfoDTO user1 = UserInfoDTO.builder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowalski")
                .email("jankowalski@example.com")
                .build();
        final UserInfoDTO user2 = UserInfoDTO.builder()
                .id(2L)
                .firstname("Anna")
                .lastname("Nowak")
                .email("annanowak@example.com")
                .build();

        final TaskInfoDTO task1 = TaskInfoDTO.builder()
                .id(1L)
                .title("Task 1")
                .description("Description for Task 1")
                .status(TaskStatus.TO_DO)
                .build();

        final TaskInfoDTO task2 = TaskInfoDTO.builder()
                .id(2L)
                .title("Task 2")
                .description("Description for Task 2")
                .status(TaskStatus.TO_DO)
                .assignedUsers(Set.of(user1, user2))
                .build();

        final TaskInfoDTO task3 = TaskInfoDTO.builder()
                .id(3L)
                .title("Task 3")
                .description("Description for Task 3")
                .status(TaskStatus.TO_DO)
                .assignedUsers(Set.of(user1))
                .build();

        return List.of(task1, task2, task3);
    }
}
