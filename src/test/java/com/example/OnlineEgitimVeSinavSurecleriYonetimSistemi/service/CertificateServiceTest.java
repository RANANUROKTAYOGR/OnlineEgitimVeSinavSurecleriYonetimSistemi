package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Certificate;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CertificateRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CertificateServiceTest {
    @Mock
    private CertificateRepository certificateRepository;
    private CertificateServiceImpl certificateService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        certificateService = new CertificateServiceImpl(certificateRepository);
    }

    @Test
    public void issueCertificate_saves() {
        Certificate c = Certificate.builder().certificateNumber("ABC").issuedAt(LocalDateTime.now()).build();
        when(certificateRepository.save(c)).thenReturn(Certificate.builder().id(1L).certificateNumber("ABC").build());
        var saved = certificateService.create(c);
        assertThat(saved.getId()).isEqualTo(1L);
    }
}

