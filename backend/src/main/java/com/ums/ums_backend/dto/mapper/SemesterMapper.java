package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.SemesterResponseDTO;
import com.ums.ums_backend.entity.Semester;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper {

    public SemesterResponseDTO toDTO(Semester semester) {
        if (semester == null) return null;

        SemesterResponseDTO dto = new SemesterResponseDTO();
        dto.setId(semester.getId());
        dto.setTerm(semester.getTerm());
        dto.setYear(semester.getYear());

        return dto;
    }

    public Semester toEntity(SemesterResponseDTO dto) {
        if (dto == null) return null;

        Semester semester = new Semester();
        semester.setId(dto.getId());
        semester.setTerm(dto.getTerm());
        semester.setYear(dto.getYear());

        return semester;
    }

}