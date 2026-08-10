package com.ums.ums_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterSummaryDTO {

    private Long id;

    @NotBlank(message = "Term is required.")
    private String term;

    @NotNull(message = "Year is required.")
    private Integer year;

}
