package com.ums.ums_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Enrollment {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    private LocalDate enrollmentDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    private Double grade;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

}
