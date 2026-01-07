package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course create(Course course);
    Optional<Course> getById(Long id);
    List<Course> getAll();
    Course update(Long id, Course course);
    void delete(Long id);

    // Convenience method aliases
    default List<Course> findAll() {
        return getAll();
    }

    default Optional<Course> findById(Long id) {
        return getById(id);
    }

    /**
     * Calculate user's progress percentage for given course (0 - 100)
     */
    Double calculateProgressPercentage(Long courseId, Long userId);
}
