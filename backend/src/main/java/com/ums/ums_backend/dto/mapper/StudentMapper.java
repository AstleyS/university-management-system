package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.EnrollmentResponseDTO;
import com.ums.ums_backend.dto.response.StudentResponseDTO;
import com.ums.ums_backend.dto.request.StudentCreateRequestDTO;
import com.ums.ums_backend.dto.summary.StudentSummaryDTO;
import com.ums.ums_backend.entity.Enrollment;
import com.ums.ums_backend.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StudentMapper {

    private UserMapper userMapper;
    private CourseMapper courseMapper;
    private SemesterMapper semesterMapper;

    public StudentResponseDTO toDTO(Student student) {

        if (student == null) {
            return null;
        }

        StudentResponseDTO dto = new StudentResponseDTO();

        dto.setId(student.getId());
        dto.setUser(userMapper.toDTO(student.getUser()));

        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());

        dto.setGender(student.getGender());
        dto.setDateOfBirth(student.getDateOfBirth());

        dto.setEmail(student.getEmail());
        if (student.getEnrollments() != null) {
            dto.setEnrollments(student.getEnrollments()
                    .stream()
                    .map(this::toEnrollmentDTO)
                    .toList());
        }

        return dto;
    }

    private EnrollmentResponseDTO toEnrollmentDTO(Enrollment enrollment) {

        if (enrollment == null) {
            return null;
        }

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();

        dto.setId(enrollment.getId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentStatus(enrollment.getEnrollmentStatus());

        if (enrollment.getCourse() != null) {
            dto.setCourse(courseMapper.toSummaryDTO(enrollment.getCourse()));
        }

        if (enrollment.getSemester() != null) {
            dto.setSemester(semesterMapper.toDTO(enrollment.getSemester()));
        }

        return dto;
    }

    public StudentSummaryDTO toSummaryDTO(Student student) {

        if (student == null) {
            return null;
        }

        StudentSummaryDTO summaryDTO = new StudentSummaryDTO();

        summaryDTO.setId(student.getId());

        summaryDTO.setFirstName(student.getFirstName());
        summaryDTO.setLastName(student.getLastName());

        summaryDTO.setGender(student.getGender());
        summaryDTO.setDateOfBirth(student.getDateOfBirth());
        summaryDTO.setEmail(student.getEmail());


        return summaryDTO;
    }



    public Student toEntity(StudentResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setId(dto.getId());

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());

        student.setEmail(dto.getEmail());

        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(dto.getGender());

        return student;
    }

    public Student toEntity(StudentCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());

        student.setEmail(dto.getEmail());

        student.setDateOfBirth(dto.getDateOfBirth());
        student.setGender(dto.getGender());

        return student;
    }

}
