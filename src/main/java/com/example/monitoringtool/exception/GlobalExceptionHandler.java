package com.example.monitoringtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputParamException.class)
    public ResponseEntity<String> handleException(InvalidInputParamException ex) {
        return new ResponseEntity<>(ex.message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.message, HttpStatus.BAD_REQUEST);
    }

}
