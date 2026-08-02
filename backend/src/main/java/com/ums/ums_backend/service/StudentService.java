package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.StudentDTO;
import com.ums.ums_backend.dto.mapper.StudentMapper;
import com.ums.ums_backend.entity.Student;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.StudentRepository;
import com.ums.ums_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final UserRepository userRepository;
    private final StudentMapper mapper;

    public List<StudentDTO> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public StudentDTO findById(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        return mapper.toDTO(student);
    }


    public StudentDTO save(StudentDTO dto) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    "Student already exists with email: " + dto.getEmail()
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


        Student student = mapper.toEntity(dto);
        student.setUser(user);

        Student saved = repository.save(student);

        return mapper.toDTO(saved);
    }


    public StudentDTO update(Long id, StudentDTO dto) {

        Student existingStudent = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        if (!existingStudent.getEmail().equals(dto.getEmail())
                && repository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    "Student already exists with email: " + dto.getEmail()
            );
        }

        existingStudent.setFirstName(dto.getFirstName());
        existingStudent.setLastName(dto.getLastName());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setDateOfBirth(dto.getDateOfBirth());
        existingStudent.setGender(dto.getGender());


        return mapper.toDTO(
                repository.save(existingStudent)
        );
    }


    public void delete(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        repository.delete(student);
    }
}