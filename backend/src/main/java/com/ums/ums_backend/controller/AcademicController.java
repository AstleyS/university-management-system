package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.response.AcademicStatsResponseDTO;
import com.ums.ums_backend.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/academics")
public class AcademicController {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final SemesterRepository semesterRepository;

    public AcademicController(StudentRepository studentRepository,
                              ProfessorRepository professorRepository,
                              CourseRepository courseRepository,
                              EnrollmentRepository enrollmentRepository,
                              DepartmentRepository departmentRepository,
                              FacultyRepository facultyRepository,
                              SemesterRepository semesterRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.semesterRepository = semesterRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @GetMapping("/counts")
    public ResponseEntity<AcademicStatsResponseDTO> getCounts() {
        AcademicStatsResponseDTO dto = new AcademicStatsResponseDTO(
                studentRepository.count(),
                professorRepository.count(),
                courseRepository.count(),
                enrollmentRepository.count(),
                departmentRepository.count(),
                facultyRepository.count(),
                semesterRepository.count()
        );

        return ResponseEntity.ok(dto);
    }

}
