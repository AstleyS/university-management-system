package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.StudentDTO;
import com.ums.ums_backend.dto.mapper.StudentMapper;
import com.ums.ums_backend.entity.Student;
import com.ums.ums_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final StudentMapper mapper;

    public StudentService(StudentRepository repository,
                          StudentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<StudentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<StudentDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public StudentDTO save(StudentDTO dto) {
        Student entity = mapper.toEntity(dto);
        Student saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setEmail(dto.getEmail());
                    existing.setDateOfBirth(dto.getDateOfBirth());
                    if (dto.getGender() != null) {
                        existing.setGender(com.ums.ums_backend.entity.Gender.valueOf(dto.getGender()));
                    }
                    Student updated = repository.save(existing);
                    return mapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
