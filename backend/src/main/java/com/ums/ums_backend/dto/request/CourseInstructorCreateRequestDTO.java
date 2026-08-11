package com.ums.ums_backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstructorCreateRequestDTO {

    @NotNull(message = "Course is required.")
    private Long courseId;

    @NotNull(message = "Professor is required.")
    private Long professorId;
}