package com.ums.ums_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Professor {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "professor")
    private List<TeachingAssignment> courses;


}
