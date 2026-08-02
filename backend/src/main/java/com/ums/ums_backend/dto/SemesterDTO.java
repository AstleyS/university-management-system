package com.ums.ums_backend.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemesterDTO {

    private Long id;

    @NotBlank(message = "Term is required.")
    private String term;

    @NotNull(message = "Year is required.")
    private Integer year;

    private List<Long> enrollmentIds = new ArrayList<>();

}