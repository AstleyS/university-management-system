package com.ums.ums_backend.dto;

import java.util.ArrayList;
import java.util.List;

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

    public CourseDTO(Long id, Long departmentId, String code, String name, String description, Double credits, List<Long> enrollmentIds, List<Long> teachingAssignmentIds) {
        this.id = id;
        this.departmentId = departmentId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.enrollmentIds = enrollmentIds == null ? new ArrayList<>() : enrollmentIds;
        this.teachingAssignmentIds = teachingAssignmentIds == null ? new ArrayList<>() : teachingAssignmentIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public List<Long> getEnrollmentIds() {
        return enrollmentIds;
    }

    public void setEnrollmentIds(List<Long> enrollmentIds) {
        this.enrollmentIds = enrollmentIds;
    }

    public List<Long> getTeachingAssignmentIds() {
        return teachingAssignmentIds;
    }

    public void setTeachingAssignmentIds(List<Long> teachingAssignmentIds) {
        this.teachingAssignmentIds = teachingAssignmentIds;
    }
}
