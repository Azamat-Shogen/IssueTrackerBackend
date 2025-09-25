package com.revature.IssueTrackerBackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserExists(UserAlreadyExistsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "User already exists");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Handle invalid input
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidInput(InvalidInputException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Invalid input provided");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
