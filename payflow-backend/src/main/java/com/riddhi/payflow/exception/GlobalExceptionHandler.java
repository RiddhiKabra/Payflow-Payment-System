package com.riddhi.payflow.exception;

import com.riddhi.payflow.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ApiResponse<?> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );
    }
}