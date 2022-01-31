package com.niemczuk.taskmanagerbackend.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private UUID taskId;

    private String name;

    private String description;

    private String status;

    private LocalDateTime creationDate;
}
