package com.ums.ums_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StudentDTO {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private List<Long> enrollmentIds = new ArrayList<>();

    public StudentDTO() {
    }

    public StudentDTO(Long id,
                      Long userId,
                      String firstName,
                      String lastName,
                      String gender,
                      LocalDate dateOfBirth,
                      String email,
                      List<Long> enrollmentIds) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.enrollmentIds = enrollmentIds == null ? new ArrayList<>() : enrollmentIds;
    }
}
