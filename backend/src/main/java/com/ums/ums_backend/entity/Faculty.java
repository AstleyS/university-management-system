package com.ums.ums_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Faculty {

    @Id
    private Long id;

    private String name;

    private String code;

    private String description;

    @OneToMany(mappedBy = "faculty")
    private List<Department> departments;

}
