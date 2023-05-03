package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.dto.requests.LoginRequest;
import com.invogue_fashionblog.dto.responses.AuthenticationResponse;
import com.invogue_fashionblog.dto.requests.RegistrationRequest;
import com.invogue_fashionblog.services.serviceimpl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/fashionblog/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegistrationRequest request){
        return userService.saveUser(request);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logUserIn(@Valid @RequestBody LoginRequest request){
    return userService.authenticateUser(request);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logUserOut(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).location(URI.create("/login")).build();
    }
}
