package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.dto.summary.CourseSummaryDTO;
import com.ums.ums_backend.dto.summary.ProfessorSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for CourseInstructor containing course and professor assignment information for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstructorResponseDTO {

    private Long id;
    private CourseSummaryDTO course;
    private ProfessorSummaryDTO professor;

}
