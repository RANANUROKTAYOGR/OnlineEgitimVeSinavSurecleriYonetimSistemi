package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course create(Course course);
    Optional<Course> getById(Long id);
    Course findById(Long id);
    List<Course> getAll();
    List<Course> findAll();
    Course update(Long id, Course course);
    void delete(Long id);

    /**
     * Calculate user's progress percentage for given course (0 - 100)
     */
    Double calculateProgressPercentage(Long courseId, Long userId);
}
