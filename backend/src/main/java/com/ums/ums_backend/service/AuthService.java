package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.auth.LoginRequest;
import com.ums.ums_backend.dto.auth.LoginResponse;
import com.ums.ums_backend.dto.auth.RegisterRequest;
import com.ums.ums_backend.dto.auth.RegisterResponse;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.repository.UserRepository;
import com.ums.ums_backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService (UserRepository repository,
                        PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest request) {

        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User savedUser = repository.save(user);

        return new RegisterResponse(savedUser.getId(),
                                    savedUser.getUsername(),
                                    savedUser.getRole());


    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtService.generateToken(user);

        return new LoginResponse(token);

    }
}
