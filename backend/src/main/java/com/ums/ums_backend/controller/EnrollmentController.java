package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.EnrollmentDTO;
import com.ums.ums_backend.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getEnrollments() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        EnrollmentDTO enrollment = service.findById(id);
        return ResponseEntity.ok(enrollment);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<EnrollmentDTO> enrollments = service.findByStudentId(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody EnrollmentDTO dto) {
        EnrollmentDTO created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id, @Valid @RequestBody EnrollmentDTO dto) {
        EnrollmentDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentDTO> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeUpdateRequest request) {
        EnrollmentDTO updated = service.updateGrade(id, request.getGrade());
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<EnrollmentDTO> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        EnrollmentDTO updated = service.changeStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @PostMapping("/{id}/drop")
    public ResponseEntity<EnrollmentDTO> dropStudent(@PathVariable Long id) {
        EnrollmentDTO updated = service.dropStudent(id);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Getter
    @Setter
    public static class GradeUpdateRequest {
        private Double grade;

    }

    @Getter
    @Setter
    public static class StatusUpdateRequest {
        private com.ums.ums_backend.entity.EnrollmentStatus status;
    }

}
