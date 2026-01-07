package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Enrollment create(Enrollment enrollment);
    Optional<Enrollment> getById(Long id);
    Enrollment findById(Long id);
    List<Enrollment> getAll();
    List<Enrollment> findAll();
    Enrollment update(Long id, Enrollment enrollment);
    void delete(Long id);
}

