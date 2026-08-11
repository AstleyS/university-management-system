package com.ums.ums_backend.dto.auth;

import com.ums.ums_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Data Transfer Object for login responses containing username, role, and authentication token.
 */
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String username;
    private Set<Role> role;
    private String token;

}
