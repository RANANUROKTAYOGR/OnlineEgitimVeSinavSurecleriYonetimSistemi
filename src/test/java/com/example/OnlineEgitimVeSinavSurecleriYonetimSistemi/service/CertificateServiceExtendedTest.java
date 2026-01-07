package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Certificate;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CertificateRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CertificateServiceImpl;
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

public class CertificateServiceExtendedTest {
    @Mock
    private CertificateRepository certificateRepository;

    @InjectMocks
    private CertificateServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Certificate cert = Certificate.builder().id(1L).certificateNumber("C001").build();
        when(certificateRepository.findById(1L)).thenReturn(Optional.of(cert));

        Optional<Certificate> result = service.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getCertificateNumber()).isEqualTo("C001");
    }

    @Test
    public void testGetById_NotFound() {
        when(certificateRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Certificate> result = service.getById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    public void testGetAll() {
        List<Certificate> certs = Arrays.asList(
            Certificate.builder().id(1L).build(),
            Certificate.builder().id(2L).build()
        );
        when(certificateRepository.findAll()).thenReturn(certs);

        List<Certificate> result = service.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void testUpdate() {
        Certificate existing = Certificate.builder().id(1L).certificateNumber("OLD").build();
        Certificate updated = Certificate.builder().id(1L).certificateNumber("NEW").build();

        when(certificateRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(certificateRepository.save(any())).thenReturn(updated);

        Certificate result = service.update(1L, updated);

        assertThat(result.getCertificateNumber()).isEqualTo("NEW");
        verify(certificateRepository).save(any());
    }

    @Test
    public void testDelete() {
        doNothing().when(certificateRepository).deleteById(1L);

        service.delete(1L);

        verify(certificateRepository).deleteById(1L);
    }
}

