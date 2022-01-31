package com.niemczuk.taskmanagerbackend.controller;

import com.niemczuk.taskmanagerbackend.model.Task;
import com.niemczuk.taskmanagerbackend.model.dto.TaskDto;
import com.niemczuk.taskmanagerbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<TaskDto>> getAllUserTasks() {
        return new ResponseEntity<>(taskService.getAllUserTasks(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable(value = "id") UUID taskId) {
        return new ResponseEntity<>(taskService.getTaskById(taskId), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.addTask(taskDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") UUID taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
