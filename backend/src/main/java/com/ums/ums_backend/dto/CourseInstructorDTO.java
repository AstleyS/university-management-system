package com.ums.ums_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstructorDTO {

    private Long id;

    @NotNull(message = "Course is required.")
    private CourseSummaryDTO course;

    @NotNull(message = "Professor is required.")
    private ProfessorSummaryDTO professor;

}
