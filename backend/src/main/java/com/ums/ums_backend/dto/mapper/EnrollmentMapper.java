package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.CourseSummaryDTO;
import com.ums.ums_backend.dto.EnrollmentDTO;
import com.ums.ums_backend.dto.SemesterSummaryDTO;
import com.ums.ums_backend.dto.StudentSummaryDTO;
import com.ums.ums_backend.entity.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentDTO toDTO(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }

        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(enrollment.getId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentStatus(enrollment.getEnrollmentStatus());

        // Build nested StudentEnrollmentDTO
        if (enrollment.getStudent() != null) {
            StudentSummaryDTO studentDTO = new StudentSummaryDTO();
            studentDTO.setId(enrollment.getStudent().getId());
            studentDTO.setFirstName(enrollment.getStudent().getFirstName());
            studentDTO.setLastName(enrollment.getStudent().getLastName());
            studentDTO.setEmail(enrollment.getStudent().getEmail());

            dto.setStudent(studentDTO);
        }

        // Build nested CourseEnrollmentDTO
        if (enrollment.getCourse() != null) {
            CourseSummaryDTO courseDTO = new CourseSummaryDTO();
            courseDTO.setId(enrollment.getCourse().getId());
            courseDTO.setCode(enrollment.getCourse().getCode());
            courseDTO.setName(enrollment.getCourse().getName());
            courseDTO.setDescription(enrollment.getCourse().getDescription());

            dto.setCourse(courseDTO);
        }

        // Build nested SemesterEnrollmentDTO
        if (enrollment.getSemester() != null) {
            SemesterSummaryDTO semesterDTO = new SemesterSummaryDTO();
            semesterDTO.setId(enrollment.getSemester().getId());
            semesterDTO.setTerm(enrollment.getSemester().getTerm());
            semesterDTO.setYear(enrollment.getSemester().getYear());

            dto.setSemester(semesterDTO);
        }

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
