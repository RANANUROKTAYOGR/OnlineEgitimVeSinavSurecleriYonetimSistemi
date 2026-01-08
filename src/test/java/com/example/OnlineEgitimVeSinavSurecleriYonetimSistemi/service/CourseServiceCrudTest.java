package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceCrudTest {
    CourseRepository repo;
    EnrollmentRepository enrollmentRepository;
    CourseServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(CourseRepository.class);
        enrollmentRepository = mock(EnrollmentRepository.class);
        service = new CourseServiceImpl(repo, enrollmentRepository);
    }

    @Test
    void create_and_find() {
        Course c = new Course(); c.setId(1L); c.setTitle("C");
        when(repo.save(c)).thenReturn(c);
        when(repo.findById(1L)).thenReturn(Optional.of(c));
        when(repo.findAll()).thenReturn(List.of(c));
        Course created = service.create(c);
        assertEquals("C", created.getTitle());
        assertEquals(c, service.findById(1L));
    }
}

