package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.EnrollmentDTO;
import com.ums.ums_backend.dto.mapper.EnrollmentMapper;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.Enrollment;
import com.ums.ums_backend.entity.EnrollmentStatus;
import com.ums.ums_backend.entity.Student;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.CourseRepository;
import com.ums.ums_backend.repository.EnrollmentRepository;
import com.ums.ums_backend.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper mapper;

    public List<EnrollmentDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        return mapper.toDTO(enrollment);
    }

    public List<EnrollmentDTO> findByStudentId(Long studentId) {
        return repository.findByStudentId(studentId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public EnrollmentDTO save(EnrollmentDTO dto) {

        if (repository.existsByStudentIdAndCourseIdAndSemesterId(
                dto.getStudent().getId(),
                dto.getCourse().getId(),
                dto.getSemester().getId())) {

            throw new AlreadyExistsException(
                    "Student is already enrolled in this course for this semester"
            );
        }

        Student student = studentRepository.findById(dto.getStudent().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + dto.getStudent().getId()
                        )
                );

        Course course = courseRepository.findById(dto.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Course not found with id: " + dto.getCourse().getId()
                        )
                );


        Enrollment enrollment = mapper.toEntity(dto);

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = repository.save(enrollment);

        return mapper.toDTO(saved);
    }

    public EnrollmentDTO update(Long id, EnrollmentDTO dto) {

        Enrollment existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        existing.setEnrollmentDate(dto.getEnrollmentDate());

        if(dto.getEnrollmentStatus() != null) {
            existing.setEnrollmentStatus(dto.getEnrollmentStatus());
        }

        return mapper.toDTO(repository.save(existing));
    }

    public EnrollmentDTO updateGrade(Long id, Double grade) {

        Enrollment enrollment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        if(grade != null && (grade < 0 || grade > 20)) {
            throw new IllegalArgumentException(
                    "Grade must be between 0 and 20"
            );
        }

        enrollment.setGrade(grade);
        return mapper.toDTO(repository.save(enrollment));
    }

    public EnrollmentDTO changeStatus(Long id, EnrollmentStatus status) {
        Enrollment enrollment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with id: " + id
                ));

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        enrollment.setEnrollmentStatus(status);
        return mapper.toDTO(repository.save(enrollment));
    }

    public EnrollmentDTO dropStudent(Long id) {
        Enrollment enrollment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with id: " + id
                ));

        enrollment.setEnrollmentStatus(com.ums.ums_backend.entity.EnrollmentStatus.DROPPED);
        return mapper.toDTO(repository.save(enrollment));
    }

    public void delete(Long id) {

        Enrollment enrollment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        repository.delete(enrollment);
    }

}
