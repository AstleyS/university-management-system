package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.request.ProfessorCreateRequestDTO;
import com.ums.ums_backend.dto.response.ProfessorResponseDTO;
import com.ums.ums_backend.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getProfessors() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getProfessorById(@PathVariable Long id) {
        ProfessorResponseDTO professor = service.findById(id);
        return ResponseEntity.ok(professor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> createProfessor(@Valid @RequestBody ProfessorCreateRequestDTO createRequestDTO) {
        ProfessorResponseDTO created = service.createProfessor(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> updateProfessor(@PathVariable Long id, @Valid @RequestBody ProfessorResponseDTO dto) {
        ProfessorResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
