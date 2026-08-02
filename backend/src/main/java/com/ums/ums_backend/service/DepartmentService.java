package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.DepartmentDTO;
import com.ums.ums_backend.dto.mapper.DepartmentMapper;
import com.ums.ums_backend.entity.Department;
import com.ums.ums_backend.entity.Faculty;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.DepartmentRepository;
import com.ums.ums_backend.repository.FacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;
    private final FacultyRepository facultyRepository;
    private final DepartmentMapper mapper;

    public List<DepartmentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public DepartmentDTO findById(Long id) {
        Department dept = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + id
                ));

        return mapper.toDTO(dept);
    }

    public DepartmentDTO save(DepartmentDTO dto) {
        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + dto.getFacultyId()
                ));

        Department entity = mapper.toEntity(dto);
        entity.setFaculty(faculty);

        Department saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public DepartmentDTO update(Long id, DepartmentDTO dto) {
        Department existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + id
                ));

        if (dto.getFacultyId() != null && !dto.getFacultyId().equals(
                existing.getFaculty() != null ? existing.getFaculty().getId() : null)) {
            Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Faculty not found with id: " + dto.getFacultyId()
                    ));
            existing.setFaculty(faculty);
        }

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getCode() != null) existing.setCode(dto.getCode());
        existing.setDescription(dto.getDescription());

        Department updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Long id) {
        Department existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + id
                ));

        repository.delete(existing);
    }
}