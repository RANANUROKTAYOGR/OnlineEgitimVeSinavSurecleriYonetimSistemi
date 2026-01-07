package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Enrollment create(Enrollment enrollment);
    Optional<Enrollment> getById(Long id);
    List<Enrollment> getAll();
    Enrollment update(Long id, Enrollment enrollment);
    void delete(Long id);

    // Convenience method aliases
    default List<Enrollment> findAll() {
        return getAll();
    }

    default Optional<Enrollment> findById(Long id) {
        return getById(id);
    }
}

