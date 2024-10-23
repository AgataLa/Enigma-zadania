package com.enigma.teamtaskmanager.controller;

import com.enigma.teamtaskmanager.dto.TaskCreateDTO;
import com.enigma.teamtaskmanager.dto.TaskFiltersDTO;
import com.enigma.teamtaskmanager.dto.TaskInfoDTO;
import com.enigma.teamtaskmanager.dto.TaskStatusDTO;
import com.enigma.teamtaskmanager.dto.TaskUpdateDTO;
import com.enigma.teamtaskmanager.dto.UsersToAssignDTO;
import com.enigma.teamtaskmanager.service.TaskService;
import com.enigma.teamtaskmanager.service.view.TaskViewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskViewService taskViewService;

    public TaskController(final TaskService taskService, final TaskViewService taskViewService) {
        this.taskService = taskService;
        this.taskViewService = taskViewService;
    }

    @GetMapping
    public ResponseEntity<List<TaskInfoDTO>> getTasks(@RequestBody TaskFiltersDTO taskFiltersDTO) {
        final List<TaskInfoDTO> filteredTasks = taskViewService.getFilteredTasks(taskFiltersDTO);
        return new ResponseEntity<>(filteredTasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskInfoDTO> getTask(@PathVariable Long taskId) {
        final TaskInfoDTO task = taskViewService.getTaskById(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> createTask(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        final Long createdTaskId = taskService.createTask(taskCreateDTO);
        return new ResponseEntity<>(createdTaskId, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody @Valid TaskUpdateDTO taskUpdateDTO) {
        taskService.updateTask(taskId, taskUpdateDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<?> changeTaskStatus(@PathVariable Long taskId, @RequestBody TaskStatusDTO taskStatusDTO) {
        taskService.changeTaskStatus(taskId, taskStatusDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<?> assignUserToTask(@PathVariable Long taskId, @RequestBody UsersToAssignDTO usersToAssignDTO) {
        taskService.assignUsersToTask(taskId, usersToAssignDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
