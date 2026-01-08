package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceProgressSnapshotTest {
    CourseRepository courseRepository;
    EnrollmentRepository enrollmentRepository;
    CourseServiceImpl service;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        enrollmentRepository = mock(EnrollmentRepository.class);
        service = new CourseServiceImpl(courseRepository, enrollmentRepository);
    }

    @Test
    void calculateProgress_returnsZero_whenNoEnrollment() {
        Course c = new Course(); c.setId(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of());
        Double pct = service.calculateProgressPercentage(1L, 10L);
        assertEquals(0.0, pct);
    }

    @Test
    void calculateProgress_handlesNullModules_and_updatesSnapshot() {
        Course c = new Course(); c.setId(2L); c.setModules(null);
        User u = new User(); u.setId(20L);
        Enrollment e = new Enrollment(); e.setCourse(c); e.setUser(u); e.setCompletedLessons(0);
        when(courseRepository.findById(2L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        when(enrollmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Double pct = service.calculateProgressPercentage(2L, 20L);
        assertEquals(0.0, pct);
        // snapshot should be set to 0
        assertEquals(0, e.getTotalLessonsSnapshot());
    }

    @Test
    void calculateProgress_computesPercentage_withModulesAndLessons() {
        Lesson l1 = new Lesson(); l1.setId(101L);
        Lesson l2 = new Lesson(); l2.setId(102L);
        Module m = new Module(); m.setLessons(Set.of(l1, l2));
        Course c = new Course(); c.setId(3L); c.setModules(Set.of(m));
        User u = new User(); u.setId(30L);
        Enrollment e = new Enrollment(); e.setCourse(c); e.setUser(u); e.setCompletedLessons(1);
        when(courseRepository.findById(3L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        when(enrollmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        Double pct = service.calculateProgressPercentage(3L, 30L);
        assertEquals(50.0, pct);
        assertEquals(2, e.getTotalLessonsSnapshot());
    }
}
