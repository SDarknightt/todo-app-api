package com.samu.todoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> personalizedException(BusinessException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                                .body(new ErrorResponse(exception.getMessage(), exception.getStatusCode().value()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handler() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Erro inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
