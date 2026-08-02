package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.ProfessorDTO;
import com.ums.ums_backend.entity.Professor;
import com.ums.ums_backend.entity.Gender;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ProfessorMapper {

    public ProfessorDTO toDTO(Professor professor) {
        if (professor == null) {
            return null;
        }

        ProfessorDTO dto = new ProfessorDTO();
        dto.setId(professor.getId());
        dto.setUserId(professor.getUser() != null ? professor.getUser().getId() : null);

        dto.setFirstName(professor.getFirstName());
        dto.setLastName(professor.getLastName());

        dto.setGender(professor.getGender() != null ? professor.getGender() : null);
        dto.setDateOfBirth(professor.getDateOfBirth());

        dto.setEmail(professor.getEmail());
        if (professor.getCourseInstructors() != null) {
            dto.setCourseIds(professor.getCourseInstructors().stream()
                    .map(ci -> ci.getCourse().getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Professor toEntity(ProfessorDTO dto) {
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

}
