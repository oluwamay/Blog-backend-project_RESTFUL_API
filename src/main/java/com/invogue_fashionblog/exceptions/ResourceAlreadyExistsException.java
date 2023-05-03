package com.invogue_fashionblog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException{
    private String resource;
    private String message;

    public ResourceAlreadyExistsException(String message, String resource, String message1) {
        super(message);
        this.resource = resource;
        this.message = message1;
    }
}
