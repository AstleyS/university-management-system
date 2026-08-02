package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.SemesterDTO;
import com.ums.ums_backend.entity.Semester;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SemesterMapper {

    public SemesterDTO toDTO(Semester semester) {
        if (semester == null) return null;

        SemesterDTO dto = new SemesterDTO();
        dto.setId(semester.getId());
        dto.setTerm(semester.getTerm());
        dto.setYear(semester.getYear());

        if (semester.getEnrollments() != null) {
            dto.setEnrollmentIds(semester.getEnrollments().stream()
                    .map(e -> e.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Semester toEntity(SemesterDTO dto) {
        if (dto == null) return null;

        Semester semester = new Semester();
        semester.setId(dto.getId());
        semester.setTerm(dto.getTerm());
        semester.setYear(dto.getYear());

        return semester;
    }

}