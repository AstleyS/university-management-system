package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.request.CourseCreateRequestDTO;
import com.ums.ums_backend.dto.response.CourseResponseDTO;
import com.ums.ums_backend.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getCourses() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO course = service.findById(id);
        return ResponseEntity.ok(course);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseCreateRequestDTO createRequestDTO) {
        CourseResponseDTO course = service.createCourse(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseResponseDTO dto) {
        CourseResponseDTO course = service.update(id, dto);
        return ResponseEntity.ok(course);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
