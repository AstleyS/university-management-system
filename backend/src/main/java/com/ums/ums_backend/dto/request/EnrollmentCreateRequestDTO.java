package com.ums.ums_backend.dto.request;

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
public class EnrollmentCreateRequestDTO {

    @NotNull(message = "Student is required.")
    private Long studentId;

    @NotNull(message = "Course is required.")
    private Long courseId;

    @NotNull(message = "Semester is required.")
    private Long semesterId;

    @NotNull(message = "Enrollment date is required.")
    private LocalDate enrollmentDate;

    @NotNull(message = "Enrollment status is required.")
    private EnrollmentStatus enrollmentStatus;

    private Double grade;
}