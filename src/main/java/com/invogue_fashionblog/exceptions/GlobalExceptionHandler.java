package com.invogue_fashionblog.exceptions;

import com.invogue_fashionblog.dto.CustomError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CustomError> resourceNotFoundException(ResourceNotFoundException ex){
        CustomError error = new CustomError(
                ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND
                );
        return ResponseEntity.status(error.getStatus()).body(error);
    }
    @ExceptionHandler(FashionBlogApiException.class)
    public ResponseEntity<CustomError> fashionBlogApiException(FashionBlogApiException e){
        CustomError error = new CustomError(e.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, error.getStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> globalExpression(Exception ex){
        CustomError error = new CustomError(ex.getMessage(), LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e
    ){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<CustomError> resourceAlreadyExists(ResourceAlreadyExistsException ex){
        CustomError error = new CustomError(ex.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(error, error.getStatus());
    }

}
