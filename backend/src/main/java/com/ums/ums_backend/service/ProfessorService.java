package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.ProfessorDTO;
import com.ums.ums_backend.dto.mapper.ProfessorMapper;
import com.ums.ums_backend.entity.Professor;
import com.ums.ums_backend.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository repository;
    private final ProfessorMapper mapper;

    public ProfessorService(ProfessorRepository repository,
                            ProfessorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProfessorDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<ProfessorDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public ProfessorDTO save(ProfessorDTO dto) {
        Professor entity = mapper.toEntity(dto);
        Professor saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public ProfessorDTO update(Long id, ProfessorDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setEmail(dto.getEmail());
                    existing.setDateOfBirth(dto.getDateOfBirth());
                    if (dto.getGender() != null) {
                        existing.setGender(dto.getGender());
                    }
                    Professor updated = repository.save(existing);
                    return mapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
