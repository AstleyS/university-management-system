package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.CourseDTO;
import com.ums.ums_backend.dto.mapper.CourseMapper;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.Department;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.CourseRepository;
import com.ums.ums_backend.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final DepartmentRepository departmentRepository;
    private final CourseMapper mapper;

    public List<CourseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CourseDTO findById(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        return mapper.toDTO(course);
    }

    public CourseDTO save(CourseDTO dto) {

        if(repository.existsByCode(dto.getCode())) {
            throw new AlreadyExistsException(
                    "Course already exists with code: " + dto.getCode()
            );
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));


        Course course = mapper.toEntity(dto);
        course.setDepartment(department);
        Course saved = repository.save(course);

        return mapper.toDTO(saved);
    }

    public CourseDTO update(Long id, CourseDTO dto) {

        Course existingCourse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        if (!existingCourse.getCode().equals(dto.getCode())
                && repository.existsByCode(dto.getCode())) {
            throw new AlreadyExistsException(
                    "Course already exists with code" + dto.getCode()
            );
        }

        existingCourse.setCode(dto.getCode());
        existingCourse.setName(dto.getName());
        existingCourse.setDescription(dto.getDescription());
        existingCourse.setCredits(dto.getCredits());
        
        // Update department if it changed
        if (!existingCourse.getDepartment().getId().equals(dto.getDepartmentId())) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + dto.getDepartmentId()));
            existingCourse.setDepartment(department);
        }

        Course updatedCourse = repository.save(existingCourse);
        return mapper.toDTO(updatedCourse);

    }

    public void delete(Long id) {
        Course course = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        repository.delete(course);
    }

}
