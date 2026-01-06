package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Certificate;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CertificateRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.CertificateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Certificate create(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public Optional<Certificate> getById(Long id) {
        return certificateRepository.findById(id);
    }

    @Override
    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }

    @Override
    public Certificate update(Long id, Certificate certificate) {
        return certificateRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(certificate, existing, "id");
            return certificateRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Certificate not found"));
    }

    @Override
    public void delete(Long id) {
        certificateRepository.deleteById(id);
    }
}

