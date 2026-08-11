package com.ums.ums_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Academic Statistics containing aggregated counts of system entities for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicStatsResponseDTO {
    private long students;
    private long professors;
    private long courses;
    private long enrollments;
    private long departments;
    private long faculties;
    private long semesters;
}
