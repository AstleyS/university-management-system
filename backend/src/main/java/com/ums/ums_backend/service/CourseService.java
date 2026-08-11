package com.ums.ums_backend.service;

import com.ums.ums_backend.dto.response.CourseResponseDTO;
import com.ums.ums_backend.dto.mapper.CourseMapper;
import com.ums.ums_backend.dto.request.CourseCreateRequestDTO;
import com.ums.ums_backend.entity.Course;
import com.ums.ums_backend.entity.Department;
import com.ums.ums_backend.exception.AlreadyExistsException;
import com.ums.ums_backend.exception.ResourceNotFoundException;
import com.ums.ums_backend.repository.CourseRepository;
import com.ums.ums_backend.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final DepartmentRepository departmentRepository;

    private final CourseMapper mapper;

    public List<CourseResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CourseResponseDTO findById(Long id) {
        Course course = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        return mapper.toDTO(course);
    }

    @Transactional
    public CourseResponseDTO createCourse(CourseCreateRequestDTO createRequestDTO) {

        if (repository.existsByCode(createRequestDTO.getCode())) {
            throw new AlreadyExistsException(
                    "Course already exists with code: " + createRequestDTO.getCode()
            );
        }

        Department department = departmentRepository.findById(createRequestDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + createRequestDTO.getDepartmentId()
                ));

        Course course = mapper.toEntity(createRequestDTO);

        course.setDepartment(department);

        Course savedCourse = repository.save(course);

        return mapper.toDTO(savedCourse);
    }

    /*
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
     */

    public void delete(Long id) {
        Course course = repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        repository.delete(course);
    }

}
