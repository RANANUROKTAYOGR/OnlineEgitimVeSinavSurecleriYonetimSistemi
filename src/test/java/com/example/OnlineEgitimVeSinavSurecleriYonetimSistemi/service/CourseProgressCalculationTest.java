package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseProgressCalculationTest {
    CourseRepository courseRepo;
    EnrollmentRepository enrollmentRepo;
    CourseServiceImpl service;

    @BeforeEach
    void setup() {
        courseRepo = mock(CourseRepository.class);
        enrollmentRepo = mock(EnrollmentRepository.class);
        service = new CourseServiceImpl(courseRepo, enrollmentRepo);
    }

    @Test
    void returnsZeroIfCourseMissing() {
        when(courseRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.calculateProgressPercentage(99L, 1L));
    }

    @Test
    void returnsZeroIfNoEnrollmentFound() {
        Course c = new Course(); c.setId(10L);
        when(courseRepo.findById(10L)).thenReturn(Optional.of(c));
        when(enrollmentRepo.findAll()).thenReturn(List.of());
        Double d = service.calculateProgressPercentage(10L, 5L);
        assertEquals(0.0, d);
    }

    @Test
    void computesFromSnapshotIfPresent() {
        Course c = new Course(); c.setId(20L);
        when(courseRepo.findById(20L)).thenReturn(Optional.of(c));
        Enrollment e = new Enrollment(); e.setCourse(c); e.setUser(null); e.setTotalLessonsSnapshot(4); e.setCompletedLessons(2);
        // create enrollment with user id
        var user = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User(); user.setId(7L);
        e.setUser(user);
        when(enrollmentRepo.findAll()).thenReturn(List.of(e));
        Double d = service.calculateProgressPercentage(20L, 7L);
        assertEquals(50.0, d);
    }

    @Test
    void ifSnapshotZero_calculatesFromCourseModules_and_savesSnapshot() {
        // course with modules and lessons
        Course c = new Course(); c.setId(30L);
        Module m1 = new Module(); m1.setLessons(new HashSet<>(List.of(new Lesson(), new Lesson())));
        Module m2 = new Module(); m2.setLessons(new HashSet<>(List.of(new Lesson())));
        c.setModules(new HashSet<>(List.of(m1, m2)));
        when(courseRepo.findById(30L)).thenReturn(Optional.of(c));

        Enrollment e = new Enrollment(); e.setCourse(c);
        var user = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User(); user.setId(11L);
        e.setUser(user);
        e.setTotalLessonsSnapshot(0);
        e.setCompletedLessons(1);
        when(enrollmentRepo.findAll()).thenReturn(List.of(e));
        when(enrollmentRepo.save(any())).thenAnswer(i -> i.getArgument(0));
        Double d = service.calculateProgressPercentage(30L, 11L);
        // total lessons = 3, completed =1 => 33.333...
        assertEquals((1 * 100.0) / 3, d);
        // verify snapshot updated to 3
        assertEquals(3, e.getTotalLessonsSnapshot());
        verify(enrollmentRepo, times(1)).save(e);
    }
}

