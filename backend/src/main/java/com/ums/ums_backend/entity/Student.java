package com.ums.ums_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Student {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

}


