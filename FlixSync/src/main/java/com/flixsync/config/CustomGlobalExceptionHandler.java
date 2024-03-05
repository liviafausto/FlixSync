package com.flixsync.config;

import com.flixsync.exceptions.DatabaseException;
import com.flixsync.exceptions.EntityNotFoundException;
import com.flixsync.exceptions.InvalidParameterException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private Map<String, Object> createBody(String message, HttpStatus status) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), status.value(), message);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", response.getTimestamp());
        body.put("status", response.getStatus());
        body.put("message", response.getMessage());
        return body;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final String MESSAGE = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(createBody(MESSAGE, HttpStatus.BAD_REQUEST), headers, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception) {
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        Map<String, Object> body = createBody(exception.getMessage(), STATUS);
        body.replace("message", "Invalid parameter provided");
        return ResponseEntity.status(STATUS).body(body);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleException(DatabaseException exception) {
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(STATUS).body(createBody(exception.getMessage(), STATUS));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException exception) {
        final HttpStatus STATUS = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(STATUS).body(createBody(exception.getMessage(), STATUS));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleException(InvalidParameterException exception) {
        final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(STATUS).body(createBody(exception.getMessage(), STATUS));
    }

}
