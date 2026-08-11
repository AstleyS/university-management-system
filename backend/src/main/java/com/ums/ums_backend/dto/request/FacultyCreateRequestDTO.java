package com.ums.ums_backend.dto.request;

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
public class FacultyCreateRequestDTO {

    @NotBlank(message = "Faculty name is required.")
    @Size(min = 3, max = 30, message = "Faculty name must be between 3 and 30 characters.")
    private String name;

    @NotBlank(message = "Faculty code is required.")
    @Size(min = 2, max = 10, message = "Faculty code must be between 2 and 10 characters.")
    private String code;

    @Size(max = 250, message = "Description cannot exceed 250 characters.")
    private String description;
}