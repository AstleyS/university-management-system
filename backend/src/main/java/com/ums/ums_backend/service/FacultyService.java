package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.FacultyDTO;
import com.ums.ums_backend.dto.mapper.FacultyMapper;
import com.ums.ums_backend.entity.Faculty;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.FacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacultyService {

    private final FacultyRepository repository;
    private final FacultyMapper mapper;

    public List<FacultyDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public FacultyDTO findById(Long id) {
        Faculty faculty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + id
                ));

        return mapper.toDTO(faculty);
    }

    public FacultyDTO save(FacultyDTO dto) {
        Faculty entity = mapper.toEntity(dto);
        Faculty saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public FacultyDTO update(Long id, FacultyDTO dto) {
        Faculty existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + id
                ));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getCode() != null) existing.setCode(dto.getCode());
        existing.setDescription(dto.getDescription());

        Faculty updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Long id) {
        Faculty existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + id
                ));

        repository.delete(existing);
    }
}