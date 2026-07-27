package com.ums.ums_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseDTO {

    private Long id;
    private Long departmentId;
    private String code;
    private String name;
    private String description;
    private Double credits;
    private List<Long> enrollmentIds = new ArrayList<>();
    private List<Long> teachingAssignmentIds = new ArrayList<>();

    public CourseDTO() {
    }

    public CourseDTO(Long id,
                     Long departmentId,
                     String code,
                     String name,
                     String description,
                     Double credits,
                     List<Long> enrollmentIds,
                     List<Long> teachingAssignmentIds) {
        this.id = id;
        this.departmentId = departmentId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.enrollmentIds = enrollmentIds == null ? new ArrayList<>() : enrollmentIds;
        this.teachingAssignmentIds = teachingAssignmentIds == null ? new ArrayList<>() : teachingAssignmentIds;
    }
}
