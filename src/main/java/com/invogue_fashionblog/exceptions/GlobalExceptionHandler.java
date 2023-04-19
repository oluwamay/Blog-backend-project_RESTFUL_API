package com.invogue_fashionblog.exceptions;

import com.invogue_fashionblog.dto.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CustomError> resourceNotFoundException(ResourceNotFoundException ex){
        CustomError error = new CustomError(
                ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND
                );
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}
