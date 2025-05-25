package com.topaz.ms_users.infrastructure.adapter.input.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.topaz.ms_users.domain.exception.UserNotFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put(TIMESTAMP, Instant.now().toString());
        errorBody.put(STATUS, HttpStatus.NOT_FOUND.value());
        errorBody.put(ERROR, "User Not Found");
        errorBody.put(MESSAGE, ex.getMessage());

        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put(TIMESTAMP, Instant.now().toString());
        errorBody.put(STATUS, HttpStatus.BAD_REQUEST.value());
        errorBody.put(ERROR, "Bad Request");
        errorBody.put(MESSAGE, ex.getMessage());

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put(TIMESTAMP, Instant.now().toString());
        errorBody.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorBody.put(ERROR, "Internal Server Error");
        errorBody.put(MESSAGE, "An unexpected error occurred: " + ex.getMessage());

        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
