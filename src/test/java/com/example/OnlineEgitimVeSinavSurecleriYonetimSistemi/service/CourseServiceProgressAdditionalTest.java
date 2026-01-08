package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.*;
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

public class CourseServiceProgressAdditionalTest {
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
    void calculateProgress_returns_zero_when_total_less_than_or_equal_zero() {
        Course c = Course.builder().id(40L).modules(Set.of()).build();
        Enrollment e = Enrollment.builder().id(10L).course(c).completedLessons(0).totalLessonsSnapshot(0).user(User.builder().id(300L).build()).build();
        when(courseRepository.findById(40L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));

        assertEquals(0.0, service.calculateProgressPercentage(40L, 300L));
    }

    @Test
    void calculateProgress_handles_nulls_in_enrollment_fields() {
        Course c = Course.builder().id(41L).modules(Set.of()).build();
        Enrollment e = Enrollment.builder().id(11L).course(c).completedLessons(null).totalLessonsSnapshot(null).user(User.builder().id(301L).build()).build();
        when(courseRepository.findById(41L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));
        when(enrollmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        assertEquals(0.0, service.calculateProgressPercentage(41L, 301L));
        // snapshot should remain 0
        assertEquals(0, e.getTotalLessonsSnapshot());
    }

}

