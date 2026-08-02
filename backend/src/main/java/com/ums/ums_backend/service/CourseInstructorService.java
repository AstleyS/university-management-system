package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.CourseDTO;
import com.ums.ums_backend.dto.CourseInstructorDTO;
import com.ums.ums_backend.dto.mapper.CourseMapper;
import com.ums.ums_backend.dto.mapper.CourseInstructorMapper;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.CourseInstructor;
import com.ums.ums_backend.entity.Professor;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.CourseInstructorRepository;
import com.ums.ums_backend.repository.CourseRepository;
import com.ums.ums_backend.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseInstructorService {

    private final CourseInstructorRepository repository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final CourseInstructorMapper mapper;
    private final CourseMapper courseMapper;

    public List<CourseInstructorDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CourseInstructorDTO findById(Long id) {
        CourseInstructor ci = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course instructor not found with id: " + id
                ));

        return mapper.toDTO(ci);
    }

    public List<CourseDTO> getCoursesByProfessorId(Long professorId) {
        return repository.findByProfessorId(professorId)
                .stream()
                .map(CourseInstructor::getCourse)
                .map(courseMapper::toDTO)
                .toList();
    }

    public CourseInstructorDTO save(CourseInstructorDTO dto) {

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course not found with id: " + dto.getCourseId()
                ));

        Professor professor = professorRepository.findById(dto.getProfessorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor not found with id: " + dto.getProfessorId()
                ));

        CourseInstructor entity = mapper.toEntity(dto);
        entity.setCourse(course);
        entity.setProfessor(professor);

        CourseInstructor saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public CourseInstructorDTO update(Long id, CourseInstructorDTO dto) {

        CourseInstructor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course instructor not found with id: " + id
                ));

        if (dto.getCourseId() != null && !dto.getCourseId().equals(
                existing.getCourse() != null ? existing.getCourse().getId() : null)) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Course not found with id: " + dto.getCourseId()
                    ));
            existing.setCourse(course);
        }

        if (dto.getProfessorId() != null && !dto.getProfessorId().equals(
                existing.getProfessor() != null ? existing.getProfessor().getId() : null)) {
            Professor professor = professorRepository.findById(dto.getProfessorId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Professor not found with id: " + dto.getProfessorId()
                    ));
            existing.setProfessor(professor);
        }

        CourseInstructor updated = repository.save(existing);
        return mapper.toDTO(updated);
    }

    public void delete(Long id) {
        CourseInstructor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course instructor not found with id: " + id
                ));

        repository.delete(existing);
    }
}
