package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.dto.summary.DepartmentSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Course containing course details and related IDs for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {

    private Long id;
    private DepartmentSummaryDTO department;
    private String code;
    private String name;
    private String description;
    private Double credits;
    private List<EnrollmentResponseDTO> enrollments = new ArrayList<>();
    private List<CourseInstructorResponseDTO> courseInstructors = new ArrayList<>();

}
