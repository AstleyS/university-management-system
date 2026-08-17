package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.CourseInstructorResponseDTO;
import com.ums.ums_backend.dto.response.ProfessorResponseDTO;
import com.ums.ums_backend.dto.request.ProfessorCreateRequestDTO;
import com.ums.ums_backend.dto.summary.ProfessorSummaryDTO;
import com.ums.ums_backend.entity.CourseInstructor;
import com.ums.ums_backend.entity.Professor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfessorMapper {

    private final CourseMapper courseMapper;
    private UserMapper userMapper;

    public ProfessorResponseDTO toDTO(Professor professor) {
        if (professor == null) {
            return null;
        }

        ProfessorResponseDTO dto = new ProfessorResponseDTO();
        dto.setId(professor.getId());
        dto.setUser(userMapper.toDTO(professor.getUser()));

        dto.setFirstName(professor.getFirstName());
        dto.setLastName(professor.getLastName());

        dto.setGender(professor.getGender() != null ? professor.getGender() : null);
        dto.setDateOfBirth(professor.getDateOfBirth());

        dto.setEmail(professor.getEmail());
        if (professor.getCourses() != null) {
            dto.setCourses(professor.getCourses()
                    .stream()
                    .map(this::toCourseInstructorDTO)
                    .toList());
        }

        return dto;
    }

    private CourseInstructorResponseDTO toCourseInstructorDTO(
            CourseInstructor courseInstructor) {

        if (courseInstructor == null) return null;

        CourseInstructorResponseDTO dto = new CourseInstructorResponseDTO();

        dto.setId(courseInstructor.getId());

        dto.setCourse(courseMapper.toSummaryDTO(courseInstructor.getCourse()));
        dto.setProfessor(toSummaryDTO(courseInstructor.getProfessor()));

        return dto;
    }


    public ProfessorSummaryDTO toSummaryDTO(Professor professor) {

        if (professor == null) {
            return null;
        }

        ProfessorSummaryDTO summaryDTO = new ProfessorSummaryDTO();
        summaryDTO.setId(professor.getId());

        summaryDTO.setFirstName(professor.getFirstName());
        summaryDTO.setLastName(professor.getLastName());

        summaryDTO.setGender(professor.getGender());
        summaryDTO.setDateOfBirth(professor.getDateOfBirth());

        summaryDTO.setEmail(professor.getEmail());

        return summaryDTO;
    }


    public Professor toEntity(ProfessorResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        Professor professor = new Professor();
        professor.setId(dto.getId());

        professor.setFirstName(dto.getFirstName());
        professor.setLastName(dto.getLastName());

        professor.setEmail(dto.getEmail());

        professor.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) {
            professor.setGender(dto.getGender());
        }

        return professor;
    }

    public Professor toEntity(ProfessorCreateRequestDTO createRequestDTO) {
        if (createRequestDTO == null) {
            return null;
        }

        Professor professor = new Professor();

        professor.setFirstName(createRequestDTO.getFirstName());
        professor.setLastName(createRequestDTO.getLastName());

        professor.setEmail(createRequestDTO.getEmail());

        professor.setDateOfBirth(createRequestDTO.getDateOfBirth());
        if (createRequestDTO.getGender() != null) {
            professor.setGender(createRequestDTO.getGender());
        }

        return professor;
    }

}
