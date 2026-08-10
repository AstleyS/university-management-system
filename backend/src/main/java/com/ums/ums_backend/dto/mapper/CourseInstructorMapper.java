package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.CourseInstructorDTO;
import com.ums.ums_backend.dto.CourseSummaryDTO;
import com.ums.ums_backend.dto.ProfessorSummaryDTO;
import com.ums.ums_backend.entity.CourseInstructor;
import org.springframework.stereotype.Component;

@Component
public class CourseInstructorMapper {

    public CourseInstructorDTO toDTO(CourseInstructor courseInstructor) {
        if (courseInstructor == null) {
            return null;
        }

        CourseInstructorDTO dto = new CourseInstructorDTO();
        dto.setId(courseInstructor.getId());

        if (courseInstructor.getCourse() != null) {
            CourseSummaryDTO courseDTO = new CourseSummaryDTO();
            courseDTO.setId(courseInstructor.getCourse().getId());
            courseDTO.setCode(courseInstructor.getCourse().getCode());
            courseDTO.setName(courseInstructor.getCourse().getName());
            courseDTO.setDescription(courseInstructor.getCourse().getDescription());

            dto.setCourse(courseDTO);
        }

        if (courseInstructor.getProfessor() != null) {
            ProfessorSummaryDTO professorDTO = new ProfessorSummaryDTO();
            professorDTO.setId(courseInstructor.getProfessor().getId());
            professorDTO.setFirstName(courseInstructor.getProfessor().getFirstName());
            professorDTO.setLastName(courseInstructor.getProfessor().getLastName());
            professorDTO.setEmail(courseInstructor.getProfessor().getEmail());

            dto.setProfessor(professorDTO);
        }

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
