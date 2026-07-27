package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.CourseDTO;
import com.ums.ums_backend.dto.mapper.CourseMapper;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper mapper;

    public CourseService(CourseRepository repository,
                         CourseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CourseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<CourseDTO> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO);
    }

    public CourseDTO save(CourseDTO dto) {
        Course entity = mapper.toEntity(dto);
        Course saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public CourseDTO update(Long id, CourseDTO dto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setCode(dto.getCode());
                    existing.setName(dto.getName());
                    existing.setDescription(dto.getDescription());
                    existing.setCredits(dto.getCredits());
                    Course updated = repository.save(existing);
                    return mapper.toDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
