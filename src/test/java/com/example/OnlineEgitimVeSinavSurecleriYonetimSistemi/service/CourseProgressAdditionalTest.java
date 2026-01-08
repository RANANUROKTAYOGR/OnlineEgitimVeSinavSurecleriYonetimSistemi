package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseProgressAdditionalTest {
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
    void progress_is_zero_when_no_enrollment() {
        Course c = new Course(); c.setId(1L);
        when(courseRepository.findById(1L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of());
        double p = service.calculateProgressPercentage(1L, 2L);
        assertEquals(0.0, p);
    }

    @Test
    void progress_calculated_from_snapshot_if_present() {
        Course c = new Course(); c.setId(2L);
        Enrollment e = new Enrollment(); e.setId(10L); e.setCourse(c); e.setUser(new User()); e.getUser().setId(5L);
        e.setTotalLessonsSnapshot(10); e.setCompletedLessons(3);
        when(courseRepository.findById(2L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        double p = service.calculateProgressPercentage(2L, 5L);
        assertEquals(30.0, p);
    }

    @Test
    void progress_computes_snapshot_when_snapshot_missing() {
        Course c = new Course(); c.setId(3L);
        com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module m1 = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module();
        m1.setLessons(new HashSet<>(Set.of(new Lesson(), new Lesson())));
        com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module m2 = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module();
        m2.setLessons(new HashSet<>(Set.of(new Lesson())));
        c.setModules(new HashSet<>(Set.of(m1, m2)));
        Enrollment e = new Enrollment(); e.setId(11L); e.setCourse(c); e.setUser(new User()); e.getUser().setId(6L);
        e.setTotalLessonsSnapshot(null); e.setCompletedLessons(2);
        when(courseRepository.findById(3L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        when(enrollmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        double p = service.calculateProgressPercentage(3L, 6L);
        assertEquals((2 * 100.0) / 3.0, p);
    }

    @Test
    void progress_returns_zero_for_zero_totalLessons() {
        Course c = new Course(); c.setId(4L); c.setModules(null);
        Enrollment e = new Enrollment(); e.setId(12L); e.setCourse(c); e.setUser(new User()); e.getUser().setId(7L);
        e.setTotalLessonsSnapshot(0); e.setCompletedLessons(0);
        when(courseRepository.findById(4L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        double p = service.calculateProgressPercentage(4L, 7L);
        assertEquals(0.0, p);
    }
}
