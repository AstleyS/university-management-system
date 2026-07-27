package com.ums.ums_backend.service;

import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> getUsers() {
        return repository.findAll();
    }

}
