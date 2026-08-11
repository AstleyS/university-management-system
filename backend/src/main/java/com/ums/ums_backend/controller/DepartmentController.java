package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.request.DepartmentCreateRequestDTO;
import com.ums.ums_backend.dto.response.DepartmentResponseDTO;
import com.ums.ums_backend.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartments() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentCreateRequestDTO createRequestDTO) {
        DepartmentResponseDTO created = service.createDepartment(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentResponseDTO dto) {
        DepartmentResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}