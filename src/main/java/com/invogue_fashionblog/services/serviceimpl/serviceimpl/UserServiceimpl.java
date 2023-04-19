package com.invogue_fashionblog.services.serviceimpl.serviceimpl;

import com.invogue_fashionblog.dto.AuthenticationResponse;
import com.invogue_fashionblog.dto.RegistrationRequest;
import com.invogue_fashionblog.repositories.UserRepository;
import com.invogue_fashionblog.services.serviceimpl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {
    private final UserRepository repository;
    @Override
    public ResponseEntity<AuthenticationResponse> saveUser(RegistrationRequest request) {

        return null;
    }
}
