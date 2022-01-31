package com.niemczuk.taskmanagerbackend.mapper;

import com.niemczuk.taskmanagerbackend.model.Task;
import com.niemczuk.taskmanagerbackend.model.dto.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto mapTaskToTaskDto(Task task);

    Task mapTaskDtoToTask(TaskDto taskDto);
}
