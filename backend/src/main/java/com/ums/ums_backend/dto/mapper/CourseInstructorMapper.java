package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.CourseInstructorDTO;
import com.ums.ums_backend.entity.CourseInstructor;
import org.springframework.stereotype.Component;

@Component
public class CourseInstructorMapper {

    public CourseInstructorDTO toDTO(CourseInstructor assignment) {
        if (assignment == null) {
            return null;
        }

        CourseInstructorDTO dto = new CourseInstructorDTO();
        dto.setId(assignment.getId());
        dto.setCourseId(assignment.getCourse() != null ? assignment.getCourse().getId() : null);
        dto.setProfessorId(assignment.getProfessor() != null ? assignment.getProfessor().getId() : null);

        return dto;
    }

    public CourseInstructor toEntity(CourseInstructorDTO dto) {
        if (dto == null) {
            return null;
        }

        CourseInstructor assignment = new CourseInstructor();
        assignment.setId(dto.getId());

        return assignment;
    }
}
