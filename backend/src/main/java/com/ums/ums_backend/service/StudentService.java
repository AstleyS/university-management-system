package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.response.StudentResponseDTO;
import com.ums.ums_backend.dto.mapper.StudentMapper;
import com.ums.ums_backend.dto.request.StudentCreateRequestDTO;
import com.ums.ums_backend.entity.Role;
import com.ums.ums_backend.entity.Student;
import com.ums.ums_backend.entity.User;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.StudentRepository;
import com.ums.ums_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    private final StudentMapper studentMapper;

    private final PasswordEncoder passwordEncoder;

    public List<StudentResponseDTO> findAll() {

        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    public StudentResponseDTO findById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        return studentMapper.toDTO(student);
    }


    @Transactional
    public StudentResponseDTO createStudent(StudentCreateRequestDTO createRequestDTO) {

        if (studentRepository.existsByEmail(createRequestDTO.getEmail())) {
            throw new AlreadyExistsException(
                    "Student already exists with email: " + createRequestDTO.getEmail()
            );
        }

        User user = new User();
        String username = "Student" + (userRepository.count() + 1);

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(username + ".test"));
        user.setRoles(Set.of(Role.STUDENT));

        User savedUser = userRepository.save(user);

        Student student = studentMapper.toEntity(createRequestDTO);
        student.setUser(savedUser);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toDTO(savedStudent);
    }


    public StudentResponseDTO update(Long id, StudentCreateRequestDTO dto) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        if (!existingStudent.getEmail().equals(dto.getEmail())
                && studentRepository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException(
                    "Student already exists with email: " + dto.getEmail()
            );
        }

        existingStudent.setFirstName(dto.getFirstName());
        existingStudent.setLastName(dto.getLastName());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setDateOfBirth(dto.getDateOfBirth());
        existingStudent.setGender(dto.getGender());


        return studentMapper.toDTO(
                studentRepository.save(existingStudent)
        );
    }


    public void delete(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        studentRepository.delete(student);
    }
}