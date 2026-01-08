package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Certificate;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CertificateRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CertificateServiceImpl;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SimpleCreateAndListTests {
    CourseRepository courseRepository;
    CertificateRepository certificateRepository;
    CourseServiceImpl courseService;
    CertificateServiceImpl certificateService;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        certificateRepository = mock(CertificateRepository.class);
        courseService = new CourseServiceImpl(courseRepository, null);
        certificateService = new CertificateServiceImpl(certificateRepository);
    }

    @Test
    void create_and_list_course() {
        Course in = Course.builder().title("C1").build();
        Course out = Course.builder().id(1L).title("C1").build();
        when(courseRepository.save(in)).thenReturn(out);
        when(courseRepository.findAll()).thenReturn(List.of(out));

        var saved = courseService.create(in);
        assertThat(saved.getId()).isEqualTo(1L);
        var all = courseService.getAll();
        assertThat(all).hasSize(1);
    }

    @Test
    void create_and_list_certificate() {
        Certificate in = Certificate.builder().certificateNumber("CERT-1").build();
        Certificate out = Certificate.builder().id(2L).certificateNumber("CERT-1").build();
        when(certificateRepository.save(in)).thenReturn(out);
        when(certificateRepository.findAll()).thenReturn(List.of(out));

        var saved = certificateService.create(in);
        assertThat(saved.getId()).isEqualTo(2L);
        var all = certificateService.getAll();
        assertThat(all).hasSize(1);
    }
}
