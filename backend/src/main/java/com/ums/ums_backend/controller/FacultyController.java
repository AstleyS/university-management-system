package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.request.FacultyCreateRequestDTO;
import com.ums.ums_backend.dto.response.FacultyResponseDTO;
import com.ums.ums_backend.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<FacultyResponseDTO>> getFaculties() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> getFacultyById(@PathVariable Long id) {
        FacultyResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FacultyResponseDTO> createFaculty(@Valid @RequestBody FacultyCreateRequestDTO createRequestDTO) {
        FacultyResponseDTO created = service.createFaculty(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> updateFaculty(@PathVariable Long id, @Valid @RequestBody FacultyResponseDTO dto) {
        FacultyResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}