package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.DepartmentDTO;
import com.ums.ums_backend.entity.Department;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    public DepartmentDTO toDTO(Department department) {
        if (department == null) return null;

        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setFacultyId(department.getFaculty() != null ? department.getFaculty().getId() : null);
        dto.setName(department.getName());
        dto.setCode(department.getCode());
        dto.setDescription(department.getDescription());

        if (department.getCourses() != null) {
            dto.setCourseIds(department.getCourses().stream()
                    .map(c -> c.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Department toEntity(DepartmentDTO dto) {
        if (dto == null) return null;

        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());

        return department;
    }

}