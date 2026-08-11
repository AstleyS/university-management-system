package com.ums.ums_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentCreateRequestDTO {

    @NotNull(message = "Faculty is required.")
    private Long facultyId;

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 30, message = "Department name must be between 3 and 30 characters.")
    private String name;

    @NotBlank(message = "Code is required.")
    @Size(min = 2, max = 10, message = "Department code must be between 2 and 10 characters.")
    private String code;

    @Size(max = 250, message = "Description cannot exceed 250 characters.")
    private String description;

}