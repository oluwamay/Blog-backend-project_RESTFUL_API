package com.invogue_fashionblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {

    private String message;
    private LocalDateTime timeStamp;
    private HttpStatus status;

}
