package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course update(Long id, Course course) {
        return courseRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(course, existing, "id");
            return courseRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Double calculateProgressPercentage(Long courseId, Long userId) {
        var courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) throw new RuntimeException("Course not found");
        var enrollmentOpt = enrollmentRepository.findAll().stream()
                .filter(e -> e.getCourse() != null && e.getCourse().getId() != null && e.getCourse().getId().equals(courseId)
                        && e.getUser() != null && e.getUser().getId() != null && e.getUser().getId().equals(userId))
                .findFirst();
        if (enrollmentOpt.isEmpty()) return 0.0;
        var enrollment = enrollmentOpt.get();
        // if snapshot zero, compute total lessons from course
        if (enrollment.getTotalLessonsSnapshot() == null || enrollment.getTotalLessonsSnapshot() == 0) {
            int total = 0;
            var modules = courseOpt.get().getModules();
            if (modules != null) {
                for (var m : modules) {
                    if (m.getLessons() != null) total += m.getLessons().size();
                }
            }
            enrollment.setTotalLessonsSnapshot(total);
            enrollmentRepository.save(enrollment);
        }
        int totalLessons = enrollment.getTotalLessonsSnapshot() != null ? enrollment.getTotalLessonsSnapshot() : 0;
        int completed = enrollment.getCompletedLessons() != null ? enrollment.getCompletedLessons() : 0;
        if (totalLessons <= 0) return 0.0;
        return (completed * 100.0) / totalLessons;
    }
}
