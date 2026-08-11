package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.request.CourseInstructorCreateRequestDTO;
import com.ums.ums_backend.dto.response.CourseInstructorResponseDTO;
import com.ums.ums_backend.entity.CourseInstructor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseInstructorMapper {

    private CourseMapper courseMapper;
    private ProfessorMapper professorMapper;

    public CourseInstructorResponseDTO toDTO(CourseInstructor courseInstructor) {
        if (courseInstructor == null) {
            return null;
        }

        CourseInstructorResponseDTO dto = new CourseInstructorResponseDTO();
        dto.setId(courseInstructor.getId());
        dto.setCourse(courseMapper.toSummaryDTO(courseInstructor.getCourse()));
        dto.setProfessor(professorMapper.toSummaryDTO(courseInstructor.getProfessor()));

        return dto;
    }

    public CourseInstructor toEntity(CourseInstructorResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        CourseInstructor assignment = new CourseInstructor();
        assignment.setId(dto.getId());

        return assignment;
    }

    public CourseInstructor toEntity(CourseInstructorCreateRequestDTO createRequestDTO) {

        if (createRequestDTO == null) {
            return null;
        }

        return new CourseInstructor();
    }
}
