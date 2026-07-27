package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.auth.RegisterRequest;
import com.ums.ums_backend.dto.auth.RegisterResponse;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService (UserRepository repository,
                        PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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

}
