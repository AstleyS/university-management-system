package com.ums.ums_backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    private String name;

    private String code;

    private String description;

    @OneToMany(mappedBy = "department")
    private List<Course> courses;

}
