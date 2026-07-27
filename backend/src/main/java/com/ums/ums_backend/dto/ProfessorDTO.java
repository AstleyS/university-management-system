package com.ums.ums_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProfessorDTO {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
    private List<Long> courseIds = new ArrayList<>();

    public ProfessorDTO() {
    }

    public ProfessorDTO(Long id,
                        Long userId,
                        String firstName,
                        String lastName,
                        String gender,
                        LocalDate dateOfBirth,
                        String email,
                        List<Long> courseIds) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.courseIds = courseIds == null ? new ArrayList<>() : courseIds;
    }
}
