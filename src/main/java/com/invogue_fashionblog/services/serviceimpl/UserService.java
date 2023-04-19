package com.invogue_fashionblog.services.serviceimpl;

import com.invogue_fashionblog.dto.AuthenticationResponse;
import com.invogue_fashionblog.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<AuthenticationResponse> saveUser(RegistrationRequest request);
}
