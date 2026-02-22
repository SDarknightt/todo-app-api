package com.samu.todoapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
    private HttpStatus statusCode;

    protected BusinessException(String message) {
        super(message);
    }

    protected BusinessException(String message, HttpStatus statusCode) {
        this(message);
        this.statusCode = statusCode;
    }
}
