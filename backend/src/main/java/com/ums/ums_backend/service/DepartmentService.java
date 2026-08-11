package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.response.DepartmentResponseDTO;
import com.ums.ums_backend.dto.mapper.DepartmentMapper;
import com.ums.ums_backend.dto.request.DepartmentCreateRequestDTO;
import com.ums.ums_backend.entity.Department;
import com.ums.ums_backend.entity.Faculty;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.DepartmentRepository;
import com.ums.ums_backend.repository.FacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    private final DepartmentMapper mapper;

    public List<DepartmentResponseDTO> findAll() {
        return departmentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public DepartmentResponseDTO findById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + id
                ));

        return mapper.toDTO(dept);
    }

    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentCreateRequestDTO dto) {

        if (departmentRepository.existsByCode(dto.getCode())) {
            throw new AlreadyExistsException(
                    "Department already exists with code: " + dto.getCode()
            );
        }

        Faculty faculty = facultyRepository.findById(dto.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + dto.getFacultyId()
                ));

        Department department = mapper.toEntity(dto);
        department.setFaculty(faculty);

        Department savedDepartment = departmentRepository.save(department);

        return mapper.toDTO(savedDepartment);
    }
    public DepartmentResponseDTO update(Long id, DepartmentResponseDTO dto) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + id
                ));

        if (dto.getFaculty().getId() != null && !dto.getFaculty().getId().equals(
                existing.getFaculty() != null ? existing.getFaculty().getId() : null)) {
            Faculty faculty = facultyRepository.findById(dto.getFaculty().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Faculty not found with id: " + dto.getFaculty().getId()
                    ));
            existing.setFaculty(faculty);
        }

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getCode() != null) existing.setCode(dto.getCode());
        existing.setDescription(dto.getDescription());

        Department updated = departmentRepository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Long id) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + id
                ));

        departmentRepository.delete(existing);
    }
}