package com.ums.ums_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademicStatsDTO {
    private long students;
    private long professors;
    private long courses;
    private long enrollments;
    private long departments;
    private long faculties;
    private long semesters;
}
