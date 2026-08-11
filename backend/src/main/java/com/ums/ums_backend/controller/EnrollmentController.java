package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.request.EnrollmentCreateRequestDTO;
import com.ums.ums_backend.dto.response.EnrollmentResponseDTO;
import com.ums.ums_backend.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollments() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> getEnrollmentById(@PathVariable Long id) {
        EnrollmentResponseDTO enrollment = service.findById(id);
        return ResponseEntity.ok(enrollment);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollmentsByStudentId(@PathVariable Long studentId) {
        List<EnrollmentResponseDTO> enrollments = service.findByStudentId(studentId);
        return ResponseEntity.ok(enrollments);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(@Valid @RequestBody EnrollmentCreateRequestDTO createRequestDTO) {
        EnrollmentResponseDTO created = service.createEnrollment(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDTO> updateEnrollment(@PathVariable Long id, @Valid @RequestBody EnrollmentResponseDTO dto) {
        EnrollmentResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @PatchMapping("/{id}/grade")
    public ResponseEntity<EnrollmentResponseDTO> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeUpdateRequest request) {
        EnrollmentResponseDTO updated = service.updateGrade(id, request.getGrade());
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<EnrollmentResponseDTO> changeStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        EnrollmentResponseDTO updated = service.changeStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @PostMapping("/{id}/drop")
    public ResponseEntity<EnrollmentResponseDTO> dropStudent(@PathVariable Long id) {
        EnrollmentResponseDTO updated = service.dropStudent(id);
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
