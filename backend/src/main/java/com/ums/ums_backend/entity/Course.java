package com.ums.ums_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course offered by a department with associated enrollments and instructors.
 */
@Entity
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 250)
    private String description;

    @Column(nullable = false)
    private Double credits;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> Enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseInstructor> courseInstructors = new ArrayList<>();

}
