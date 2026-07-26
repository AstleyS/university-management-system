package com.ums.ums_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Enrollment {

    @Id
    private Long id;

    private LocalDate enrollmentDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

}
