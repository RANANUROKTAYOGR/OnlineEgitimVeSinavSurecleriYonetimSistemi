package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.EnrollmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment create(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Optional<Enrollment> getById(Long id) {
        return enrollmentRepository.findById(id);
    }

    @Override
    public List<Enrollment> getAll() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment update(Long id, Enrollment enrollment) {
        return enrollmentRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(enrollment, existing, "id");
            return enrollmentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    @Override
    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }
}

