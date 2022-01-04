package com.niemczuk.taskmanagerbackend.exception;

import com.niemczuk.taskmanagerbackend.exception.classes.PermissionDeniedException;
import com.niemczuk.taskmanagerbackend.exception.classes.UsernameTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .details(request.getDescription(true))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public final ResponseEntity<Object> handlePermissionDeniedException(PermissionDeniedException exception, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .details(request.getDescription(true))
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .details(request.getDescription(true))
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public final ResponseEntity<Object> handleUsernameTakenException(UsernameTakenException exception, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .details(request.getDescription(true))
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        ExceptionResponse response = ExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .details(request.getDescription(true))
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
