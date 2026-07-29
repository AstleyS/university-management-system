package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.EnrollmentDTO;
import com.ums.ums_backend.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getEnrollments() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        Optional<EnrollmentDTO> enrollment = service.findById(id);
        return enrollment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<EnrollmentDTO> enrollments = service.findByStudentId(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody EnrollmentDTO dto) {
        EnrollmentDTO created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentDTO dto) {
        try {
            EnrollmentDTO updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentDTO> updateGrade(@PathVariable Long id, @RequestBody GradeUpdateRequest request) {
        try {
            EnrollmentDTO updated = service.updateGrade(id, request.getGrade());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public static class GradeUpdateRequest {
        private Double grade;

        public Double getGrade() {
            return grade;
        }

        public void setGrade(Double grade) {
            this.grade = grade;
        }
    }

}
