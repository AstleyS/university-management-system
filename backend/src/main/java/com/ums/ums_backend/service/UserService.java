package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.UserDTO;
import com.ums.ums_backend.dto.mapper.UserMapper;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDTO> getUsers() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return mapper.toDTO(user);
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public UserDTO updateUser(Long id, UserDTO updatedUser) {

        User existingUser = repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setRoles(updatedUser.getRoles());

        return mapper.toDTO(repository.save(existingUser));
    }

    public void deleteUser(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        repository.delete(user);
    }
}