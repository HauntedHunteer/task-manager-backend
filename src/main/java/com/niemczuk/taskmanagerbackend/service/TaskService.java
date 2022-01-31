package com.niemczuk.taskmanagerbackend.service;

import com.niemczuk.taskmanagerbackend.exception.classes.PermissionDeniedException;
import com.niemczuk.taskmanagerbackend.mapper.TaskMapper;
import com.niemczuk.taskmanagerbackend.model.AppUser;
import com.niemczuk.taskmanagerbackend.model.Task;
import com.niemczuk.taskmanagerbackend.model.dto.TaskDto;
import com.niemczuk.taskmanagerbackend.repository.AppUserRepository;
import com.niemczuk.taskmanagerbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> getAllUserTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return taskRepository.findAllByOwner_Username(auth.getName());
    }

    public TaskDto getTaskById(UUID taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return taskRepository.findByTaskIdAndOwner_Username(taskId, auth.getName()).orElseThrow(
                () -> new EntityNotFoundException("not found"));
    }

    public TaskDto addTask(TaskDto taskDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser loggedUser = appUserRepository.findByUsername(auth.getName());

        Task newTask = taskMapper.mapTaskDtoToTask(taskDto);
        newTask.setCreationDate(LocalDateTime.now());
        newTask.setOwner(loggedUser);

        return taskMapper.mapTaskToTaskDto(taskRepository.save(newTask));
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUser loggedUser = appUserRepository.findByUsername(auth.getName());

        Task taskFromDb = taskRepository.findById(taskDto.getTaskId()).orElseThrow(
                () -> new EntityNotFoundException("not found"));

        if (taskFromDb.getOwner().getUsername().equals(auth.getName())) {
            Task taskToUpdate = taskMapper.mapTaskDtoToTask(taskDto);
            taskToUpdate.setOwner(loggedUser);
            return taskMapper.mapTaskToTaskDto(taskRepository.save(taskToUpdate));
        } else {
            throw new PermissionDeniedException("permission denied");
        }
    }

    public void deleteTask(UUID taskId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Task taskToDelete = taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException("not found"));

        if (taskToDelete.getOwner().getUsername().equals(auth.getName())) {
            taskRepository.delete(taskToDelete);
        } else {
            throw new PermissionDeniedException("permission denied");
        }
    }
}
