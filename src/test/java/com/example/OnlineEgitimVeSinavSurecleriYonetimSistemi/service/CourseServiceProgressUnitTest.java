package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
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

public class CourseServiceProgressUnitTest {
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
    void calculateProgress_throws_when_course_not_found() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.calculateProgressPercentage(1L, 2L));
        assertTrue(ex.getMessage().toLowerCase().contains("course not found"));
    }

    @Test
    void calculateProgress_returns_zero_when_no_enrollment() {
        Course c = Course.builder().id(10L).modules(Set.of()).build();
        when(courseRepository.findById(10L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of());
        assertEquals(0.0, service.calculateProgressPercentage(10L, 100L));
    }

    @Test
    void calculateProgress_uses_snapshot_if_present_and_computes_percentage() {
        Course c = Course.builder().id(20L).modules(Set.of()).build();
        Enrollment e = Enrollment.builder().id(1L).course(c).completedLessons(2).totalLessonsSnapshot(4).user(User.builder().id(100L).build()).build();
        when(courseRepository.findById(20L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));

        double percent = service.calculateProgressPercentage(20L, 100L);
        assertEquals(50.0, percent);
        // confirm that repository.save might have been called only when snapshot null — here snapshot present, so no save
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void calculateProgress_computes_total_from_course_modules_and_saves_snapshot() {
        Lesson l1 = Lesson.builder().id(1L).build();
        Lesson l2 = Lesson.builder().id(2L).build();
        Module m1 = Module.builder().id(11L).lessons(Set.of(l1, l2)).build();
        Course c = Course.builder().id(30L).modules(Set.of(m1)).build();
        Enrollment e = Enrollment.builder().id(2L).course(c).completedLessons(1).totalLessonsSnapshot(0).user(User.builder().id(200L).build()).build();
        when(courseRepository.findById(30L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        when(enrollmentRepository.save(any(Enrollment.class))).thenAnswer(inv -> inv.getArgument(0));

        double percent = service.calculateProgressPercentage(30L, 200L);
        assertEquals(50.0, percent);
        // snapshot should be set to 2
        assertEquals(2, e.getTotalLessonsSnapshot());
        verify(enrollmentRepository).save(e);
    }
}

