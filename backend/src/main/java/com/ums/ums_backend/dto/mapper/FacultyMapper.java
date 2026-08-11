package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.request.FacultyCreateRequestDTO;
import com.ums.ums_backend.dto.response.FacultyResponseDTO;
import com.ums.ums_backend.dto.summary.DepartmentSummaryDTO;
import com.ums.ums_backend.dto.summary.FacultySummaryDTO;
import com.ums.ums_backend.entity.Department;
import com.ums.ums_backend.entity.Faculty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FacultyMapper {

    public FacultyResponseDTO toDTO(Faculty faculty) {

        if (faculty == null) return null;

        FacultyResponseDTO dto = new FacultyResponseDTO();

        dto.setId(faculty.getId());
        dto.setName(faculty.getName());
        dto.setCode(faculty.getCode());
        dto.setDescription(faculty.getDescription());

        if (faculty.getDepartments() != null) {
            dto.setDepartments(
                    faculty.getDepartments()
                            .stream()
                            .map(this::toDepartmentSummaryDTO)
                            .toList()
            );
        }

        return dto;
    }

    private DepartmentSummaryDTO toDepartmentSummaryDTO(Department department) {

        if (department == null) return null;

        DepartmentSummaryDTO dto = new DepartmentSummaryDTO();

        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCode(department.getCode());
        dto.setDescription(department.getDescription());

        return dto;
    }

    public FacultySummaryDTO toSummaryDTO(Faculty faculty) {

        if (faculty == null) return null;

        FacultySummaryDTO summaryDTO = new FacultySummaryDTO();

        summaryDTO.setId(faculty.getId());
        summaryDTO.setName(faculty.getName());
        summaryDTO.setCode(faculty.getCode());
        summaryDTO.setDescription(faculty.getDescription());

        return summaryDTO;
    }

    public Faculty toEntity(FacultyResponseDTO dto) {
        if (dto == null) return null;

        Faculty faculty = new Faculty();
        faculty.setId(dto.getId());
        faculty.setName(dto.getName());
        faculty.setCode(dto.getCode());
        faculty.setDescription(dto.getDescription());

        return faculty;
    }

    public Faculty toEntity(FacultyCreateRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Faculty faculty = new Faculty();

        faculty.setName(dto.getName());
        faculty.setCode(dto.getCode());
        faculty.setDescription(dto.getDescription());

        return faculty;
    }

}