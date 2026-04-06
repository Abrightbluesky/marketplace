package com.ruth.shop.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import com.ruth.shop.dto.ApiResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRunTime(RuntimeException ex){
        return ResponseEntity.badRequest().body(
            ApiResponse.builder()
            .message((ex.getMessage()))
            .data(null)
            .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneral(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ApiResponse.builder()
            .message("Internal Server Error")
            .data((null))
            .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();
        return ResponseEntity.badRequest().body(
            ApiResponse.builder()
                        .message(message)
                        .data(null)
                        .build()
        );                           
    }
    
}
