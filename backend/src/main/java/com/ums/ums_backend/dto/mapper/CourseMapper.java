package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.CourseDTO;
import com.ums.ums_backend.entity.Course;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setDepartmentId(course.getDepartment() != null ? course.getDepartment().getId() : null);
        dto.setCode(course.getCode());

        dto.setName(course.getName());
        dto.setDescription(course.getDescription());

        dto.setCredits(course.getCredits());
        if (course.getEnrollments() != null) {
            dto.setEnrollmentIds(course.getEnrollments().stream()
                    .map(e -> e.getId())
                    .collect(Collectors.toList()));
        }
        if (course.getCourseInstructors() != null) {
            dto.setTeachingAssignmentIds(course.getCourseInstructors().stream()
                    .map(ci -> ci.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Course toEntity(CourseDTO dto) {
        if (dto == null) {
            return null;
        }

        Course course = new Course();
        course.setId(dto.getId());
        course.setCode(dto.getCode());

        course.setName(dto.getName());
        course.setDescription(dto.getDescription());

        course.setCredits(dto.getCredits());

        return course;
    }

}
