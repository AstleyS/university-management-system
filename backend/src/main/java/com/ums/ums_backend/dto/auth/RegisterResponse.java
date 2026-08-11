package com.ums.ums_backend.dto.auth;

import com.ums.ums_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Data Transfer Object for registration responses containing user ID, username, and assigned roles.
 */
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private Long id;
    private String username;
    private Set<Role> role;

}
