package com.ums.ums_backend.dto.mapper;

import com.ums.ums_backend.dto.response.CourseInstructorResponseDTO;
import com.ums.ums_backend.dto.response.CourseResponseDTO;
import com.ums.ums_backend.dto.request.CourseCreateRequestDTO;
import com.ums.ums_backend.dto.response.EnrollmentResponseDTO;
import com.ums.ums_backend.dto.summary.CourseSummaryDTO;
import com.ums.ums_backend.dto.summary.ProfessorSummaryDTO;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.CourseInstructor;
import com.ums.ums_backend.entity.Department;
import com.ums.ums_backend.entity.Enrollment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseMapper {

    private final DepartmentMapper departmentMapper;
    private final SemesterMapper semesterMapper;

    /**
     * Maps a Course entity to the full CourseDTO.
     */
    public CourseResponseDTO toDTO(Course course) {

        if (course == null) {
            return null;
        }

        CourseResponseDTO dto = new CourseResponseDTO();

        dto.setId(course.getId());
        dto.setCode(course.getCode());

        dto.setDepartment(departmentMapper.toSummaryDTO(course.getDepartment()));

        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCredits(course.getCredits());

        if (course.getEnrollments() != null) {
            dto.setEnrollments(
                    course.getEnrollments()
                            .stream()
                            .map(this::toEnrollmentDTO)
                            .toList()
            );
        }

        if (course.getCourseInstructors() != null) {
            dto.setCourseInstructors(
                    course.getCourseInstructors()
                            .stream()
                            .map(this::toCourseInstructorDTO)
                            .toList()
            );
        }

        return dto;
    }

    private CourseInstructorResponseDTO toCourseInstructorDTO(
            CourseInstructor courseInstructor) {

        if (courseInstructor == null) return null;

        CourseInstructorResponseDTO dto = new CourseInstructorResponseDTO();

        dto.setId(courseInstructor.getId());

        if (courseInstructor.getProfessor() != null) {

            ProfessorSummaryDTO professorSummaryDTO = new ProfessorSummaryDTO(
                    courseInstructor.getProfessor().getId(),
                    courseInstructor.getProfessor().getFirstName(),
                    courseInstructor.getProfessor().getLastName(),
                    courseInstructor.getProfessor().getGender(),
                    courseInstructor.getProfessor().getDateOfBirth(),
                    courseInstructor.getProfessor().getEmail()
            );

            dto.setProfessor(professorSummaryDTO);
        }

        return dto;
    }

    private EnrollmentResponseDTO toEnrollmentDTO(Enrollment enrollment) {

        if (enrollment == null) {
            return null;
        }

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();

        dto.setId(enrollment.getId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentStatus(enrollment.getEnrollmentStatus());

        if (enrollment.getCourse() != null) {
            dto.setCourse(toSummaryDTO(enrollment.getCourse()));
        }

        if (enrollment.getSemester() != null) {
            dto.setSemester(semesterMapper.toDTO(enrollment.getSemester()));
        }

        return dto;
    }


    /**
     * Maps a Course entity to a lightweight CourseSummaryDTO.
     *
     * Used when Course is included inside another DTO
     * to avoid circular/nested object graphs.
     */
    public CourseSummaryDTO toSummaryDTO(Course course) {

        if (course == null) {
            return null;
        }

        CourseSummaryDTO dto = new CourseSummaryDTO();

        dto.setId(course.getId());
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setCredits(course.getCredits());

        return dto;
    }

    /**
     * Maps a CourseDTO to a Course entity.
     *
     * Relationships are represented by IDs in the DTO.
     * The service layer is responsible for loading the
     * actual related entities when necessary.
     */
    public Course toEntity(CourseResponseDTO dto) {

        if (dto == null) {
            return null;
        }

        Course course = new Course();

        course.setId(dto.getId());
        course.setCode(dto.getCode());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setCredits(dto.getCredits());

        if (dto.getDepartment() != null) {

            Department department = new Department();
            department.setId(dto.getDepartment().getId());

            course.setDepartment(department);
        }

        return course;
    }

    public Course toEntity(CourseCreateRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        Course course = new Course();

        course.setCode(dto.getCode());
        course.setName(dto.getName());
        course.setDescription(dto.getDescription());
        course.setCredits(dto.getCredits());

        return course;
    }

}