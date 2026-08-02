package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.EnrollmentDTO;
import com.ums.ums_backend.entity.Enrollment;
import com.ums.ums_backend.entity.EnrollmentStatus;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentDTO toDTO(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setSemesterId(enrollment.getSemester() != null ? enrollment.getSemester().getId() : null);

        dto.setEnrollmentDate(enrollment.getEnrollmentDate());

        dto.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null);
        dto.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);

        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentStatus(enrollment.getEnrollmentStatus() != null ? enrollment.getEnrollmentStatus() : null);

        return dto;
    }

    public Enrollment toEntity(EnrollmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setId(dto.getId());

        enrollment.setEnrollmentDate(dto.getEnrollmentDate());

        enrollment.setGrade(dto.getGrade());
        if (dto.getEnrollmentStatus() != null) {
            enrollment.setEnrollmentStatus(dto.getEnrollmentStatus());
        }

        return enrollment;
    }

}
