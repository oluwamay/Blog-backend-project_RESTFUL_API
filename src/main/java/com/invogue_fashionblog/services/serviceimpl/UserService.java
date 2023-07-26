package com.invogue_fashionblog.services.serviceimpl;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.invogue_fashionblog.dto.requests.LoginRequest;
import com.invogue_fashionblog.dto.responses.AuthenticationResponse;
import com.invogue_fashionblog.dto.requests.RegistrationRequest;
import com.invogue_fashionblog.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    ResponseEntity<AuthenticationResponse> saveUser(RegistrationRequest request);

    ResponseEntity<AuthenticationResponse> authenticateUser(LoginRequest request);

    Optional<User> getUser(String userEmail);
    ResponseEntity<String> deactivateAccount(Long userId);
}
