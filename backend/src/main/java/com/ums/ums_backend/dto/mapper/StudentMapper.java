package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.StudentDTO;
import com.ums.ums_backend.entity.Student;
import com.ums.ums_backend.entity.Gender;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public StudentDTO toDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setUserId(student.getUser() != null ? student.getUser().getId() : null);

        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());

        dto.setGender(student.getGender() != null ? student.getGender().toString() : null);
        dto.setDateOfBirth(student.getDateOfBirth());

        dto.setEmail(student.getEmail());
        if (student.getEnrollments() != null) {
            dto.setEnrollmentIds(student.getEnrollments().stream()
                    .map(e -> e.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Student toEntity(StudentDTO dto) {
        if (dto == null) {
            return null;
        }

        Student student = new Student();
        student.setId(dto.getId());

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());

        student.setEmail(dto.getEmail());

        student.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) {
            student.setGender(Gender.valueOf(dto.getGender()));
        }

        return student;
    }

}
