package com.ums.ums_backend.dto;

import com.ums.ums_backend.entity.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {

    private Long id;

    @NotNull(message = "Semester is required.")
    private Long semesterId;

    @NotNull(message = "Enrolment date is required.")
    private LocalDate enrollmentDate;

    @NotNull(message = "Student is required.")
    private Long studentId;

    @NotNull(message = "Course is required.")
    private Long courseId;

    private Double grade;

    @NotNull(message = "Enrolment status is required.")
    private EnrollmentStatus enrollmentStatus;

    private StudentEnrollmentDTO student;
    private CourseEnrollmentDTO course;
    private SemesterEnrollmentDTO semester;

}
