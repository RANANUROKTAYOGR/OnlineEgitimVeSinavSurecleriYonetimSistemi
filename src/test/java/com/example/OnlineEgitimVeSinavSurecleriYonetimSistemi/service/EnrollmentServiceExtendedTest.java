package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EnrollmentServiceExtendedTest {
    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Enrollment enrollment = Enrollment.builder().id(1L).build();
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        Optional<Enrollment> result = service.getById(1L);

        assertThat(result).isPresent();
    }

    @Test
    public void testGetById_NotFound() {
        when(enrollmentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Enrollment> result = service.getById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    public void testGetAll() {
        List<Enrollment> enrollments = Arrays.asList(
            Enrollment.builder().id(1L).build(),
            Enrollment.builder().id(2L).build()
        );
        when(enrollmentRepository.findAll()).thenReturn(enrollments);

        List<Enrollment> result = service.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void testUpdate() {
        Enrollment existing = Enrollment.builder().id(1L).completedLessons(5).build();
        Enrollment updated = Enrollment.builder().id(1L).completedLessons(10).build();

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(enrollmentRepository.save(any())).thenReturn(updated);

        Enrollment result = service.update(1L, updated);

        assertThat(result.getCompletedLessons()).isEqualTo(10);
    }

    @Test
    public void testDelete() {
        doNothing().when(enrollmentRepository).deleteById(1L);

        service.delete(1L);

        verify(enrollmentRepository).deleteById(1L);
    }
}

