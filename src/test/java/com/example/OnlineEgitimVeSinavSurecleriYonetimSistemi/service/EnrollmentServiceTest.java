package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.EnrollmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EnrollmentServiceTest {
    @Mock
    private EnrollmentRepository enrollmentRepository;

    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        enrollmentService = new EnrollmentServiceImpl(enrollmentRepository);
    }

    @Test
    public void createEnrollment_savesAndReturns() {
        Enrollment e = Enrollment.builder().build();
        when(enrollmentRepository.save(e)).thenReturn(Enrollment.builder().id(5L).build());
        var saved = enrollmentService.create(e);
        assertThat(saved.getId()).isEqualTo(5L);
    }
}

