package com.niemczuk.taskmanagerbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;
}
