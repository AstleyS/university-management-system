package com.ums.ums_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Course {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String code;

    private String name;

    private String description;

    private Double credits;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private List<TeachingAssignment> teachingAssignments;

}
