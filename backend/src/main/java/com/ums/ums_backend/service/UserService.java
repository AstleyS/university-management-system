package com.ums.ums_backend.service;

import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setRole(updatedUser.getRole());

        return repository.save(existingUser);
    }

    public void deleteUser(Long id) {

        User user = getUserById(id);

        repository.delete(user);
    }
}