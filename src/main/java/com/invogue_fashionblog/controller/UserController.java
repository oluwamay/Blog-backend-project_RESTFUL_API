package com.invogue_fashionblog.controller;

import com.invogue_fashionblog.dto.AuthenticationResponse;
import com.invogue_fashionblog.dto.RegistrationRequest;
import com.invogue_fashionblog.services.serviceimpl.UserService;
import com.invogue_fashionblog.services.serviceimpl.serviceimpl.UserServiceimpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody RegistrationRequest request){
        return userService.saveUser(request);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logUserIn(){
    return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }
}
