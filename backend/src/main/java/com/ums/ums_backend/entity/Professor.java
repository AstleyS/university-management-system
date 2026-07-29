package com.ums.ums_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Professor {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Email
    private String email;

    @OneToMany(mappedBy = "professor")
    private List<CourseInstructor> courseInstructors;


}
