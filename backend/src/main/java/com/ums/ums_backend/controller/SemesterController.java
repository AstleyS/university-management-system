package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.response.SemesterResponseDTO;
import com.ums.ums_backend.service.SemesterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService service;

    public SemesterController(SemesterService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SemesterResponseDTO>> getSemesters() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SemesterResponseDTO> getSemesterById(@PathVariable Long id) {
        SemesterResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SemesterResponseDTO> createSemester(@Valid @RequestBody SemesterResponseDTO dto) {
        SemesterResponseDTO created = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponseDTO> updateSemester(@PathVariable Long id, @Valid @RequestBody SemesterResponseDTO dto) {
        SemesterResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}