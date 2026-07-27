package com.ums.ums_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentDTO {

    private Long id;
    private Long semesterId;
    private LocalDate enrollmentDate;
    private Long studentId;
    private Long courseId;
    private Double grade;
    private String enrollmentStatus;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(Long id,
                         Long semesterId,
                         LocalDate enrollmentDate,
                         Long studentId,
                         Long courseId,
                         Double grade,
                         String enrollmentStatus) {
        this.id = id;
        this.semesterId = semesterId;
        this.enrollmentDate = enrollmentDate;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
        this.enrollmentStatus = enrollmentStatus;
    }
}
