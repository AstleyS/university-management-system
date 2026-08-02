package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.CourseDTO;
import com.ums.ums_backend.dto.CourseInstructorDTO;
import com.ums.ums_backend.service.CourseInstructorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-instructors")
public class CourseInstructorController {

    private final CourseInstructorService service;

    public CourseInstructorController(CourseInstructorService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<CourseInstructorDTO>> getCourseInstructors() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseInstructorDTO> getCourseInstructorById(@PathVariable Long id) {
        CourseInstructorDTO assignment = service.findById(id);
        return ResponseEntity.ok(assignment);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/professor/{professorId}/courses")
    public ResponseEntity<List<CourseDTO>> getCoursesByProfessor(@PathVariable Long professorId) {
        List<CourseDTO> courses = service.getCoursesByProfessorId(professorId);
        return ResponseEntity.ok(courses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseInstructorDTO> createCourseInstructor(@Valid @RequestBody CourseInstructorDTO dto) {
        CourseInstructorDTO created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseInstructorDTO> updateCourseInstructor(@PathVariable Long id, @Valid @RequestBody CourseInstructorDTO dto) {
        CourseInstructorDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseInstructor(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
