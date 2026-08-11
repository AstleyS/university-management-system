package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.response.SemesterResponseDTO;
import com.ums.ums_backend.dto.mapper.SemesterMapper;
import com.ums.ums_backend.entity.Semester;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.SemesterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SemesterService {

    private final SemesterRepository repository;
    private final SemesterMapper mapper;

    public List<SemesterResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public SemesterResponseDTO findById(Long id) {
        Semester semester = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Semester not found with id: " + id
                ));

        return mapper.toDTO(semester);
    }

    public SemesterResponseDTO save(SemesterResponseDTO dto) {
        Semester entity = mapper.toEntity(dto);
        Semester saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public SemesterResponseDTO update(Long id, SemesterResponseDTO dto) {
        Semester existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Semester not found with id: " + id
                ));

        if (dto.getTerm() != null) existing.setTerm(dto.getTerm());
        if (dto.getYear() != null) existing.setYear(dto.getYear());

        Semester updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Long id) {
        Semester existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Semester not found with id: " + id
                ));

        repository.delete(existing);
    }
}