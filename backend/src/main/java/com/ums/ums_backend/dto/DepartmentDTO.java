package com.ums.ums_backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;

    @NotNull(message = "Faculty is required.")
    private Long facultyId;

    @NotBlank(message = "Department name is required.")
    @Size(min = 3, max = 30, message = "Department name must be between 3 and 30 characters.")
    private String name;

    @NotBlank(message = "Department code is required.")
    @Size(min = 2, max = 10, message = "Department code must be between 2 and 10 characters.")
    private String code;

    @Size(max = 250, message = "Description cannot exceed 250 characters.")
    private String description;

    private List<Long> courseIds = new ArrayList<>();

}