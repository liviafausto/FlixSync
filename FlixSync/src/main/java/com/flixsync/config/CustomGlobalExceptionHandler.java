package com.flixsync.config;

import com.flixsync.exceptions.DatabaseException;
import com.flixsync.exceptions.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private <T extends Exception> Map<String, Object> createBody(T exception, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", exception.getMessage());
        return body;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception) {
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(STATUS).body(createBody(exception, STATUS));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleException(DatabaseException exception) {
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(STATUS).body(createBody(exception, STATUS));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException exception) {
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(STATUS).body(createBody(exception, STATUS));
    }

}
