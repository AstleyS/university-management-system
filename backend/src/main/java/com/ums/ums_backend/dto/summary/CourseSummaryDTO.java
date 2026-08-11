package com.ums.ums_backend.dto.summary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSummaryDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Double credits;

}
