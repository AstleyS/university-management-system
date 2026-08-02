package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.FacultyDTO;
import com.ums.ums_backend.entity.Faculty;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FacultyMapper {

    public FacultyDTO toDTO(Faculty faculty) {
        if (faculty == null) return null;

        FacultyDTO dto = new FacultyDTO();
        dto.setId(faculty.getId());
        dto.setName(faculty.getName());
        dto.setCode(faculty.getCode());
        dto.setDescription(faculty.getDescription());

        if (faculty.getDepartments() != null) {
            dto.setDepartmentIds(faculty.getDepartments().stream()
                    .map(d -> d.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Faculty toEntity(FacultyDTO dto) {
        if (dto == null) return null;

        Faculty faculty = new Faculty();
        faculty.setId(dto.getId());
        faculty.setName(dto.getName());
        faculty.setCode(dto.getCode());
        faculty.setDescription(dto.getDescription());

        return faculty;
    }

}