package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.CourseDTO;
import com.ums.ums_backend.dto.CourseInstructorDTO;
import com.ums.ums_backend.dto.mapper.CourseMapper;
import com.ums.ums_backend.dto.mapper.CourseInstructorMapper;
import com.ums.ums_backend.entity.CourseInstructor;
import com.ums.ums_backend.repository.CourseInstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseInstructorService {

    private final CourseInstructorRepository repository;
    private final CourseInstructorMapper mapper;
    private final CourseMapper courseMapper;

    public CourseInstructorService(CourseInstructorRepository repository,
                                      CourseInstructorMapper mapper,
                                      CourseMapper courseMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.courseMapper = courseMapper;
    }

    public List<CourseInstructorDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<CourseInstructorDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public List<CourseDTO> getCoursesByProfessorId(Long professorId) {
        return repository.findByProfessorId(professorId)
                .stream()
                .map(CourseInstructor::getCourse)
                .map(courseMapper::toDTO)
                .toList();
    }

    public CourseInstructorDTO save(CourseInstructorDTO dto) {
        CourseInstructor entity = mapper.toEntity(dto);
        CourseInstructor saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public CourseInstructorDTO update(Long id, CourseInstructorDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setId(dto.getId());
                    CourseInstructor updated = repository.save(existing);
                    return mapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Course Instructor not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
