package com.ums.ums_backend.controller;

import com.ums.ums_backend.dto.response.StudentResponseDTO;
import com.ums.ums_backend.dto.request.StudentCreateRequestDTO;
import com.ums.ums_backend.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getStudents() {
        return ResponseEntity.ok(service.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR', 'STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = service.findById(id);
        return ResponseEntity.ok(student);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentCreateRequestDTO createRequestDTO) {
        StudentResponseDTO student = service.createStudent(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    /*
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentCreateRequestDTO createRequestDTO) {
        StudentResponseDTO student = service.update(id, createRequestDTO);
        return ResponseEntity.ok(student);
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
