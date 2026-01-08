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

public class CourseCrudTest {
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
    void create_and_getAll_and_getById() {
        Course c = new Course(); c.setId(1L); c.setTitle("T");
        when(courseRepo.save(c)).thenReturn(c);
        when(courseRepo.findAll()).thenReturn(List.of(c));
        when(courseRepo.findById(1L)).thenReturn(Optional.of(c));
        Course created = service.create(c);
        assertEquals(c, created);
        assertEquals(1, service.getAll().size());
        assertTrue(service.getById(1L).isPresent());
    }

    @Test
    void findById_throws_when_missing() {
        when(courseRepo.findById(5L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(5L));
    }

    @Test
    void update_throws_when_missing() {
        when(courseRepo.findById(9L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(9L, new Course()));
    }

    @Test
    void delete_calls_repo() {
        doNothing().when(courseRepo).deleteById(3L);
        service.delete(3L);
        verify(courseRepo, times(1)).deleteById(3L);
    }
}

