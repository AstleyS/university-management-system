package com.ums.ums_backend.repository;

import com.ums.ums_backend.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByStudentIdAndCourseIdAndSemesterId(
            Long studentId,
            Long courseId,
            Long semesterId
    );
}
