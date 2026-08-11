package com.ums.ums_backend.dto.summary;

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
public class FacultySummaryDTO {

    private Long id;
    private String name;
    private String code;
    private String description;

}