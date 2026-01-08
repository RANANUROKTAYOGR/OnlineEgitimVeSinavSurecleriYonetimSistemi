package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
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

public class CourseServiceCalculateProgressTest {
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
    void whenCourseNotFound_thenThrow() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.calculateProgressPercentage(1L, 2L));
        assertTrue(ex.getMessage().contains("Course not found"));
    }

    @Test
    void whenEnrollmentNotFound_thenReturnZero() {
        Course c = new Course();
        c.setId(10L);
        when(courseRepository.findById(10L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of());
        Double val = service.calculateProgressPercentage(10L, 5L);
        assertEquals(0.0, val);
    }

    @Test
    void whenSnapshotNull_thenComputeFromCourseAndSaveAndReturnPercentage() {
        // build course with 2 modules, each with lessons
        Lesson l1 = new Lesson(); l1.setId(1L);
        Lesson l2 = new Lesson(); l2.setId(2L);
        Lesson l3 = new Lesson(); l3.setId(3L);
        Lesson l4 = new Lesson(); l4.setId(4L);

        Module m1 = new Module(); m1.setLessons(Set.of(l1, l2));
        Module m2 = new Module(); m2.setLessons(Set.of(l3, l4));

        Course c = new Course(); c.setId(20L); c.setModules(Set.of(m1, m2));

        Enrollment e = new Enrollment();
        e.setId(100L);
        e.setCourse(c);
        var user = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User();
        user.setId(9L);
        e.setUser(user);
        e.setCompletedLessons(2);
        e.setTotalLessonsSnapshot(null);

        when(courseRepository.findById(20L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        when(enrollmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Double percent = service.calculateProgressPercentage(20L, 9L);
        // total lessons = 4, completed 2 => 50.0
        assertEquals(50.0, percent);
        // verify saved with snapshot set
        verify(enrollmentRepository, times(1)).save(argThat(en -> en.getTotalLessonsSnapshot() != null && en.getTotalLessonsSnapshot() == 4));
    }

    @Test
    void whenTotalLessonsZero_thenReturnZero() {
        Course c = new Course(); c.setId(30L); c.setModules(Set.of());
        Enrollment e = new Enrollment();
        e.setCourse(c);
        var user = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User(); user.setId(7L);
        e.setUser(user);
        e.setCompletedLessons(0);
        e.setTotalLessonsSnapshot(0);

        when(courseRepository.findById(30L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));

        Double pct = service.calculateProgressPercentage(30L, 7L);
        assertEquals(0.0, pct);
    }
}
