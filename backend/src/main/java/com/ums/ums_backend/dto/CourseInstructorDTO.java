package com.ums.ums_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstructorDTO {

    private Long id;
    private Long courseId;
    private Long professorId;

}
