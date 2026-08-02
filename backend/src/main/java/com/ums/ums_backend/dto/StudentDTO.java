package com.ums.ums_backend.dto;

import com.ums.ums_backend.entity.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    @NotNull(message = "User is required.")
    private Long userId;

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank(message = "Gender is required.")
    private Gender gender;

    @NotNull(message = "Date of Birth is required.")
    @Past(message = "Date of Birth must be in the past.")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    private List<Long> enrollmentIds = new ArrayList<>();
}
