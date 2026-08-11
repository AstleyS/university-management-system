package com.ums.ums_backend.repository;

import com.ums.ums_backend.entity.CourseInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseInstructorRepository extends JpaRepository<CourseInstructor, Long> {
    List<CourseInstructor> findByProfessorId(Long professorId);
    boolean existsByCourseIdAndProfessorId(Long courseId, Long professorId);
}
