package com.niemczuk.taskmanagerbackend.exception.classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameTakenException extends RuntimeException {

    public UsernameTakenException(String message) {
        super(message);
    }
}
