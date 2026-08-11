package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.request.CourseCreateRequestDTO;
import com.ums.ums_backend.dto.request.CourseInstructorCreateRequestDTO;
import com.ums.ums_backend.dto.response.CourseResponseDTO;
import com.ums.ums_backend.dto.response.CourseInstructorResponseDTO;
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
    public ResponseEntity<List<CourseInstructorResponseDTO>> getCourseInstructors() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseInstructorResponseDTO> getCourseInstructorById(@PathVariable Long id) {
        CourseInstructorResponseDTO assignment = service.findById(id);
        return ResponseEntity.ok(assignment);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/professor/{professorId}/courses")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByProfessor(@PathVariable Long professorId) {
        List<CourseResponseDTO> courses = service.getCoursesByProfessorId(professorId);
        return ResponseEntity.ok(courses);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseInstructorResponseDTO> createCourseInstructor(@Valid @RequestBody CourseInstructorCreateRequestDTO createRequestDTO) {
        CourseInstructorResponseDTO created = service.associateCourseInstructor(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseInstructorResponseDTO> updateCourseInstructor(@PathVariable Long id, @Valid @RequestBody CourseInstructorResponseDTO dto) {
        CourseInstructorResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseInstructor(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
