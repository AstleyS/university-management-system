package com.ums.ums_backend.dto.request;

import com.ums.ums_backend.entity.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfessorCreateRequestDTO {

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 30, message = "First name should be between 3-30 characters")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 30, message = "Last name should be between 3-30 characters")
    private String lastName;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Date of Birth is required.")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;
}

