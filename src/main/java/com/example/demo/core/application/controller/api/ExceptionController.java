package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.ApiError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ApiError> handle(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(new ApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handle(NotFoundException exception){
        return new ResponseEntity<>(new ApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handle(MissingPathVariableException exception){
        return new ResponseEntity<>(new ApiError(LocalDateTime.now(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
