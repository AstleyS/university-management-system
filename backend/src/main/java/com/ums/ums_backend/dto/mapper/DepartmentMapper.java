package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.DepartmentResponseDTO;
import com.ums.ums_backend.dto.request.DepartmentCreateRequestDTO;
import com.ums.ums_backend.dto.summary.CourseSummaryDTO;
import com.ums.ums_backend.dto.summary.DepartmentSummaryDTO;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.Department;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DepartmentMapper {

    private FacultyMapper facultyMapper;

    public DepartmentResponseDTO toDTO(Department department) {
        if (department == null) return null;

        DepartmentResponseDTO dto = new DepartmentResponseDTO();

        dto.setId(department.getId());
        dto.setFaculty(facultyMapper.toSummaryDTO(department.getFaculty()));
        dto.setName(department.getName());
        dto.setCode(department.getCode());
        dto.setDescription(department.getDescription());

        if (department.getCourses() != null) {
            dto.setCourses(department.getCourses()
                    .stream()
                    .map(this::toCourseSummaryDTO)
                    .toList());
        }

        return dto;
    }

    private CourseSummaryDTO toCourseSummaryDTO(Course course) {

        if (course == null) return null;

        CourseSummaryDTO dto = new CourseSummaryDTO();

        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());

        return dto;
    }

    public DepartmentSummaryDTO toSummaryDTO(Department department) {
        if (department == null) return null;

        DepartmentSummaryDTO summaryDTO = new DepartmentSummaryDTO();
        summaryDTO.setId(department.getId());
        summaryDTO.setName(department.getName());
        summaryDTO.setCode(department.getCode());
        summaryDTO.setDescription(department.getDescription());

        return summaryDTO;
    }

    public Department toEntity(DepartmentResponseDTO dto) {
        if (dto == null) return null;

        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());

        return department;
    }

    public Department toEntity(DepartmentCreateRequestDTO createRequestDTO) {

        if (createRequestDTO == null) {
            return null;
        }

        Department department = new Department();

        department.setName(createRequestDTO.getName());
        department.setCode(createRequestDTO.getCode());
        department.setDescription(createRequestDTO.getDescription());

        return department;
    }

}