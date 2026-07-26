package com.ums.ums_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TeachingAssignment {

    @Id
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Professor professor;

}
