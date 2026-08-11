package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.request.FacultyCreateRequestDTO;
import com.ums.ums_backend.dto.response.FacultyResponseDTO;
import com.ums.ums_backend.dto.mapper.FacultyMapper;
import com.ums.ums_backend.entity.Faculty;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.FacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FacultyService {

    private final FacultyRepository repository;
    private final FacultyMapper mapper;

    public List<FacultyResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public FacultyResponseDTO findById(Long id) {
        Faculty faculty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + id
                ));

        return mapper.toDTO(faculty);
    }

    @Transactional
    public FacultyResponseDTO createFaculty(FacultyCreateRequestDTO createRequestDTO) {

        if (repository.existsByCode(createRequestDTO.getCode())) {
            throw new AlreadyExistsException(
                    "Faculty already exists with code: " + createRequestDTO.getCode()
            );
        }

        Faculty faculty = mapper.toEntity(createRequestDTO);
        Faculty savedFaculty = repository.save(faculty);

        return mapper.toDTO(savedFaculty);
    }

    /*
    public FacultyResponseDTO update(Long id, Faculty faculty) {
        Faculty existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + id
                ));

        if (faculty.getName() != null) existing.setName(faculty.getName());
        if (faculty.getCode() != null) existing.setCode(faculty.getCode());
        existing.setDescription(faculty.getDescription());

        Faculty updated = repository.save(existing);
        return mapper.toDTO(updated);
    }
     */

    public void delete(Long id) {
        Faculty existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Faculty not found with id: " + id
                ));

        repository.delete(existing);
    }
}