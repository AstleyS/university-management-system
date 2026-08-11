package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.dto.summary.CourseSummaryDTO;
import com.ums.ums_backend.dto.summary.StudentSummaryDTO;
import com.ums.ums_backend.entity.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object for Enrollment containing enrollment details, grade, and status information for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {

    private Long id;
    private SemesterResponseDTO semester;
    private LocalDate enrollmentDate;
    private StudentSummaryDTO student;
    private CourseSummaryDTO course;
    private Double grade;
    private EnrollmentStatus enrollmentStatus;

}
