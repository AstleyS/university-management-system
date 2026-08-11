package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.dto.summary.CourseSummaryDTO;
import com.ums.ums_backend.dto.summary.FacultySummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Department containing department details and related IDs for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDTO {

    private Long id;
    private FacultySummaryDTO faculty;
    private String name;
    private String code;
    private String description;
    private List<CourseSummaryDTO> courses = new ArrayList<>();

}