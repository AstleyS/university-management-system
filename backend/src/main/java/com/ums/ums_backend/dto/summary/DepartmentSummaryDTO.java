package com.ums.ums_backend.dto.summary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSummaryDTO {

    private Long id;
    private String name;
    private String code;
    private String description;

}
