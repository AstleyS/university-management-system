package com.ums.ums_backend.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * Data Transfer Object for login requests containing username and password for authentication.
 */
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "User is required")
    @Size(min=3, max = 15)
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}
