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
public class CourseDTO {

    private Long id;

    @NotNull(message = "Department is required.")
    private Long departmentId;

    @NotBlank(message = "Course code is required.")
    @Size(min = 2, max = 10, message = "Course code must be between 2 and 10 characters.")
    private String code;

    @NotBlank(message = "Course name is required.")
    @Size(min = 3, max = 30, message = "Course name must be between 3 and 30 characters.")
    private String name;

    @Size(max = 250, message = "Description cannot exceed 250 characters.")
    private String description;

    @NotNull(message = "Credits are required.")
    @Positive
    private Double credits;

    private List<Long> enrollmentIds = new ArrayList<>();
    private List<Long> courseInstructors = new ArrayList<>();

}
