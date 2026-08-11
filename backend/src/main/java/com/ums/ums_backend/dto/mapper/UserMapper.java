package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.UserResponseDTO;
import com.ums.ums_backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserMapper {

    public UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        if (user.getRoles() != null) {
            dto.setRoles(new HashSet<>(user.getRoles()));
        }

        return dto;
    }

}
