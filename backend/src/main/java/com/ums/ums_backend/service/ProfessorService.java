package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.ProfessorDTO;
import com.ums.ums_backend.dto.mapper.ProfessorMapper;
import com.ums.ums_backend.entity.Professor;
import com.ums.ums_backend.entity.Student;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.ProfessorRepository;
import com.ums.ums_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository repository;
    private final UserRepository userRepository;
    private final ProfessorMapper mapper;

    public List<ProfessorDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ProfessorDTO findById(Long id) {

        Professor professor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + id
                ));

        return mapper.toDTO(professor);
    }

    public ProfessorDTO save(ProfessorDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    "Professor already exists with email: " + dto.getEmail()
            );
        }

        if (repository.existsByUserId(dto.getUserId())) {
            throw new AlreadyExistsException(
                    "User is already assigned to a student"
            );
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "User not found with id: " + dto.getUserId()
                        )
                );

        Professor professor = mapper.toEntity(dto);
        professor.setUser(user);

        Professor saved = repository.save(professor);

        return mapper.toDTO(saved);
    }

    public ProfessorDTO update(Long id, ProfessorDTO dto) {

        Professor existingProfessor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Professor not found with id: " + id
                        )
                );

        if (!existingProfessor.getEmail().equals(dto.getEmail())
                && repository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    "Professor already exists with email: " + dto.getEmail()
            );
        }

        existingProfessor.setFirstName(dto.getFirstName());
        existingProfessor.setLastName(dto.getLastName());
        existingProfessor.setEmail(dto.getEmail());
        existingProfessor.setDateOfBirth(dto.getDateOfBirth());
        existingProfessor.setGender(dto.getGender());

        return mapper.toDTO(existingProfessor);
    }

    public void delete(Long id) {

        Professor professor = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Professor not found with id: " + id
                        )
                );

        repository.delete(professor);

        repository.deleteById(id);
    }

}
