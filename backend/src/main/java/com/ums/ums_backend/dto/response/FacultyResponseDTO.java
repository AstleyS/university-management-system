package com.ums.ums_backend.dto.response;

import com.ums.ums_backend.dto.summary.DepartmentSummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Faculty containing faculty details and department information for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyResponseDTO {

    private Long id;
    private String name;
    private String code;
    private String description;
    private List<DepartmentSummaryDTO> departments = new ArrayList<>();

}