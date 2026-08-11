package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.EnrollmentResponseDTO;
import com.ums.ums_backend.dto.request.EnrollmentCreateRequestDTO;
import com.ums.ums_backend.entity.Enrollment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EnrollmentMapper {

    private SemesterMapper semesterMapper;
    private StudentMapper studentMapper;
    private CourseMapper courseMapper;

    public EnrollmentResponseDTO toDTO(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setId(enrollment.getId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentStatus(enrollment.getEnrollmentStatus());

        dto.setStudent(studentMapper.toSummaryDTO(enrollment.getStudent()));
        dto.setCourse(courseMapper.toSummaryDTO(enrollment.getCourse()));
        dto.setSemester(semesterMapper.toDTO(enrollment.getSemester()));

        return dto;
    }

    public Enrollment toEntity(EnrollmentResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());
        enrollment.setEnrollmentDate(dto.getEnrollmentDate());
        enrollment.setGrade(dto.getGrade());
        enrollment.setEnrollmentStatus(dto.getEnrollmentStatus());

        return enrollment;
    }

    public Enrollment toEntity(EnrollmentCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(dto.getEnrollmentDate());
        enrollment.setGrade(dto.getGrade());
        enrollment.setEnrollmentStatus(dto.getEnrollmentStatus());

        return enrollment;
    }

}
