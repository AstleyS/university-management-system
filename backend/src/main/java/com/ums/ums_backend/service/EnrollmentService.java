package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.response.EnrollmentResponseDTO;
import com.ums.ums_backend.dto.mapper.EnrollmentMapper;
import com.ums.ums_backend.dto.request.EnrollmentCreateRequestDTO;
import com.ums.ums_backend.entity.*;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.CourseRepository;
import com.ums.ums_backend.repository.EnrollmentRepository;
import com.ums.ums_backend.repository.SemesterRepository;
import com.ums.ums_backend.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    private final EnrollmentMapper mapper;

    public List<EnrollmentResponseDTO> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public EnrollmentResponseDTO findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        return mapper.toDTO(enrollment);
    }

    public List<EnrollmentResponseDTO> findByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional
    public EnrollmentResponseDTO createEnrollment(EnrollmentCreateRequestDTO createRequestDTO) {

        if (enrollmentRepository.existsByStudentIdAndCourseIdAndSemesterId(
                createRequestDTO.getStudentId(),
                createRequestDTO.getCourseId(),
                createRequestDTO.getSemesterId())) {

            throw new AlreadyExistsException(
                    "Student is already enrolled in this course for this semester"
            );
        }

        Student student = studentRepository.findById(createRequestDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Student not found with id: " + createRequestDTO.getStudentId()
                        )
                );

        Course course = courseRepository.findById(createRequestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Course not found with id: " + createRequestDTO.getCourseId()
                        )
                );

        Semester semester = semesterRepository.findById(createRequestDTO.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Semester not found with id: " + createRequestDTO.getSemesterId()
                        )
                );

        Enrollment enrollment = mapper.toEntity(createRequestDTO);

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(semester);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        return mapper.toDTO(savedEnrollment);
    }

    public EnrollmentResponseDTO update(Long id, EnrollmentResponseDTO dto) {

        Enrollment existing = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        existing.setEnrollmentDate(dto.getEnrollmentDate());

        if(dto.getEnrollmentStatus() != null) {
            existing.setEnrollmentStatus(dto.getEnrollmentStatus());
        }

        return mapper.toDTO(enrollmentRepository.save(existing));
    }

    public EnrollmentResponseDTO updateGrade(Long id, Double grade) {

        Enrollment enrollment = enrollmentRepository.findById(id)
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
        return mapper.toDTO(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponseDTO changeStatus(Long id, EnrollmentStatus status) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with id: " + id
                ));

        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        enrollment.setEnrollmentStatus(status);
        return mapper.toDTO(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponseDTO dropStudent(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enrollment not found with id: " + id
                ));

        enrollment.setEnrollmentStatus(com.ums.ums_backend.entity.EnrollmentStatus.DROPPED);
        return mapper.toDTO(enrollmentRepository.save(enrollment));
    }

    public void delete(Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Enrollment not found with id: " + id
                        )
                );

        enrollmentRepository.delete(enrollment);
    }

}
