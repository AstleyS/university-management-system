package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Data Transfer Object for User containing user credentials and role information for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private Set<Role> roles;

}
