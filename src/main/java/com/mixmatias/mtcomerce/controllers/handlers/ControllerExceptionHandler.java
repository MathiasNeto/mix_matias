package com.mixmatias.mtcomerce.controllers.handlers;

import com.mixmatias.mtcomerce.dto.CustomError;
import com.mixmatias.mtcomerce.dto.FieldMessage;
import com.mixmatias.mtcomerce.dto.ValidationError;
import com.mixmatias.mtcomerce.services.exceptions.DatabaseException;
import com.mixmatias.mtcomerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        CustomError err = new CustomError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> dataBase(DatabaseException e, HttpServletRequest request){
        CustomError err = new CustomError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError err = new ValidationError(
                Instant.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Invalid dates",
                request.getRequestURI());
        for(FieldError f : e.getBindingResult().getFieldErrors()){
            err.getErrors().add(new FieldMessage(f.getField(), f.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}
