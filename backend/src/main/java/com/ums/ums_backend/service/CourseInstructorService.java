package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.request.CourseInstructorCreateRequestDTO;
import com.ums.ums_backend.dto.response.CourseResponseDTO;
import com.ums.ums_backend.dto.response.CourseInstructorResponseDTO;
import com.ums.ums_backend.dto.mapper.CourseMapper;
import com.ums.ums_backend.dto.mapper.CourseInstructorMapper;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.CourseInstructor;
import com.ums.ums_backend.entity.Professor;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.CourseInstructorRepository;
import com.ums.ums_backend.repository.CourseRepository;
import com.ums.ums_backend.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseInstructorService {

    private final CourseInstructorRepository repository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final CourseInstructorMapper mapper;
    private final CourseMapper courseMapper;

    public List<CourseInstructorResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CourseInstructorResponseDTO findById(Long id) {
        CourseInstructor ci = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course instructor not found with id: " + id
                ));

        return mapper.toDTO(ci);
    }

    public List<CourseResponseDTO> getCoursesByProfessorId(Long professorId) {
        return repository.findByProfessorId(professorId)
                .stream()
                .map(CourseInstructor::getCourse)
                .map(courseMapper::toDTO)
                .toList();
    }

    @Transactional
    public CourseInstructorResponseDTO associateCourseInstructor(CourseInstructorCreateRequestDTO createRequestDTO) {

        if (repository.existsByCourseIdAndProfessorId(
                createRequestDTO.getCourseId(),
                createRequestDTO.getProfessorId()
        )) {
            throw new AlreadyExistsException(
                    "This professor is already assigned to this course."
            );
        }

        Course course = courseRepository.findById(createRequestDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + createRequestDTO.getCourseId()
                ));

        Professor professor = professorRepository.findById(createRequestDTO.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + createRequestDTO.getProfessorId()
                ));

        CourseInstructor entity = mapper.toEntity(createRequestDTO);
        entity.setCourse(course);
        entity.setProfessor(professor);

        CourseInstructor savedCourseInstructor = repository.save(entity);
        return mapper.toDTO(savedCourseInstructor);
    }

    /*
    public CourseInstructorResponseDTO update(Long id, CourseInstructorResponseDTO dto) {

        CourseInstructor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course instructor not found with id: " + id
                ));

        if (dto.getCourse().getId() != null && !dto.getCourse().getId().equals(
                existing.getCourse() != null ? existing.getCourse().getId() : null)) {
            Course course = courseRepository.findById(dto.getCourse().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Course not found with id: " + dto.getCourse().getId()
                    ));
            existing.setCourse(course);
        }

        if (dto.getProfessor().getId() != null && !dto.getProfessor().getId().equals(
                existing.getProfessor() != null ? existing.getProfessor().getId() : null)) {
            Professor professor = professorRepository.findById(dto.getProfessor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Professor not found with id: " + dto.getProfessor().getId()
                    ));
            existing.setProfessor(professor);
        }

        CourseInstructor updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

     */

    public void delete(Long id) {
        CourseInstructor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course instructor not found with id: " + id
                ));

        repository.delete(existing);
    }
}
