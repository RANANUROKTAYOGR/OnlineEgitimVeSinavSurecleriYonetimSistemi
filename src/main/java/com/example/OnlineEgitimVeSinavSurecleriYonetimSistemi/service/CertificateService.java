package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService {
    Certificate create(Certificate certificate);
    Optional<Certificate> getById(Long id);
    List<Certificate> getAll();
    Certificate update(Long id, Certificate certificate);
    void delete(Long id);
}

