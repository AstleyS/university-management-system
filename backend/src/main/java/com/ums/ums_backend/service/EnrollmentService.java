package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.EnrollmentDTO;
import com.ums.ums_backend.dto.mapper.EnrollmentMapper;
import com.ums.ums_backend.entity.Enrollment;
import com.ums.ums_backend.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final EnrollmentMapper mapper;

    public EnrollmentService(EnrollmentRepository repository,
                             EnrollmentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<EnrollmentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<EnrollmentDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public List<EnrollmentDTO> findByStudentId(Long studentId) {
        return repository.findByStudentId(studentId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public EnrollmentDTO save(EnrollmentDTO dto) {
        Enrollment entity = mapper.toEntity(dto);
        Enrollment saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public EnrollmentDTO update(Long id, EnrollmentDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setEnrollmentDate(dto.getEnrollmentDate());
                    existing.setGrade(dto.getGrade());
                    if (dto.getEnrollmentStatus() != null) {
                        existing.setEnrollmentStatus(com.ums.ums_backend.entity.EnrollmentStatus.valueOf(dto.getEnrollmentStatus()));
                    }
                    Enrollment updated = repository.save(existing);
                    return mapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
    }

    public EnrollmentDTO updateGrade(Long id, Double grade) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setGrade(grade);
                    Enrollment updated = repository.save(existing);
                    return mapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
