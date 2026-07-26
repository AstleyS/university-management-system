package com.ums.ums_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Student {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Email
    private String email;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

}




