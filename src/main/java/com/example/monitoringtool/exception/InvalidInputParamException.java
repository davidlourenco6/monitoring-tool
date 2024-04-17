package com.example.monitoringtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputParamException extends Throwable {

    String message;
    public InvalidInputParamException(String message) {
        this.message = message;
    }
}
