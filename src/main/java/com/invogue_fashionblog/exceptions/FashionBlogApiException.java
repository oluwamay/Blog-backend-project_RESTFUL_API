package com.invogue_fashionblog.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
public class FashionBlogApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public FashionBlogApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public FashionBlogApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

}
