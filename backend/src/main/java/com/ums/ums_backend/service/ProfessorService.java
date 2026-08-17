package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.response.ProfessorResponseDTO;
import com.ums.ums_backend.dto.mapper.ProfessorMapper;
import com.ums.ums_backend.dto.request.ProfessorCreateRequestDTO;
import com.ums.ums_backend.entity.Professor;
import com.ums.ums_backend.entity.Role;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.ProfessorRepository;
import com.ums.ums_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;

    private final ProfessorMapper professorMapper;

    private final PasswordEncoder passwordEncoder;


    public List<ProfessorResponseDTO> findAll() {
        return professorRepository.findAll()
                .stream()
                .map(professorMapper::toDTO)
                .toList();
    }

    public ProfessorResponseDTO findById(Long id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with id: " + id
                ));

        return professorMapper.toDTO(professor);
    }

    @Transactional
    public ProfessorResponseDTO createProfessor(ProfessorCreateRequestDTO createRequestDTO) {

        if (professorRepository.existsByEmail(createRequestDTO.getEmail())) {
            throw new AlreadyExistsException(
                    "Professor already exists with email: " + createRequestDTO.getEmail()
            );
        }

        User user = new User();
        String username = "Professor" + (professorRepository.count() + 1);

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(username + ".test"));
        user.setRoles(Set.of(Role.PROFESSOR));

        User savedUser = userRepository.save(user);

        Professor professor = professorMapper.toEntity(createRequestDTO);
        professor.setUser(savedUser);

        Professor savedProfessor = professorRepository.save(professor);

        return professorMapper.toDTO(savedProfessor);
    }

    /*
    public ProfessorResponseDTO update(Long id, ProfessorCreateRequestDTO createRequestDTO) {

        Professor existingProfessor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Professor not found with id: " + id
                        )
                );

        if (!existingProfessor.getEmail().equals(createRequestDTO.getEmail())
                && professorRepository.existsByEmail(createRequestDTO.getEmail())) {
            throw new AlreadyExistsException(
                    "Professor already exists with email: " + createRequestDTO.getEmail()
            );
        }

        existingProfessor.setFirstName(createRequestDTO.getFirstName());
        existingProfessor.setLastName(createRequestDTO.getLastName());
        existingProfessor.setEmail(createRequestDTO.getEmail());
        existingProfessor.setDateOfBirth(createRequestDTO.getDateOfBirth());
        existingProfessor.setGender(createRequestDTO.getGender());

        return professorMapper.toDTO(existingProfessor);
    }
     */

    public void delete(Long id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Professor not found with id: " + id
                        )
                );

        professorRepository.delete(professor);
    }

}
