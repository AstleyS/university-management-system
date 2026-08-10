package com.ums.ums_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorSummaryDTO {

    private Long id;

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

}
