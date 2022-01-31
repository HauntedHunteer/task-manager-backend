package com.niemczuk.taskmanagerbackend.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private UUID taskId;

    @NotBlank(message = "Task name is mandatory")
    @Size(min = 2, max = 100, message = "Task name must have 2 - 100 characters")
    private String name;

    @NotBlank(message = "Task description is mandatory")
    @Size(min = 10, max = 255, message = "Task name must have 10 - 255 characters")
    private String description;

    @NotBlank(message = "Task status is mandatory")
    private String status;

    private LocalDateTime creationDate;
}
