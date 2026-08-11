package com.ums.ums_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Semester containing term and year information for API communication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterResponseDTO {

    private Long id;
    private String term;
    private Integer year;

}