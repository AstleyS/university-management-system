package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Professor containing professor details and course assignments for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorResponseDTO {

    private Long id;
    private UserResponseDTO user;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String email;
    private List<CourseInstructorResponseDTO> courses = new ArrayList<>();

}
