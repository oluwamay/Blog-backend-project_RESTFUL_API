package com.invogue_fashionblog.services.serviceimpl.serviceimpl;

import com.invogue_fashionblog.config.securityconfig.JWTService;
import com.invogue_fashionblog.dto.requests.LoginRequest;
import com.invogue_fashionblog.dto.responses.AuthenticationResponse;
import com.invogue_fashionblog.dto.requests.RegistrationRequest;
import com.invogue_fashionblog.entities.User;
import com.invogue_fashionblog.exceptions.ResourceAlreadyExistsException;
import com.invogue_fashionblog.exceptions.ResourceNotFoundException;
import com.invogue_fashionblog.repositories.UserRepository;
import com.invogue_fashionblog.services.serviceimpl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceimpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<AuthenticationResponse> saveUser(RegistrationRequest request) {
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new ResourceAlreadyExistsException("User email already exists", "User email",request.getEmail());
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword()
        );

        log.info("user>>>>>>>>>" + user);

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken).message("Account successfully created").build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AuthenticationResponse> authenticateUser(LoginRequest request) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
      var user= repository.findByEmail(request.getEmail()).orElseThrow(()->new ResourceNotFoundException("User", "User", request.getEmail()));
      var jwtToken = jwtService.generateToken(user);
       return  new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken).isAuthenticated(true).message("Log in successful").build(), HttpStatus.OK);
    }

    @Override
    public Optional<User> getUser(String userEmail) {
        return repository.findByEmail(userEmail);
    }
}
