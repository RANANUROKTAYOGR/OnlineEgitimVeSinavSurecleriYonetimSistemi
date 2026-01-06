package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionService {
    Submission create(Submission submission);
    Optional<Submission> getById(Long id);
    List<Submission> getAll();
    Submission update(Long id, Submission submission);
    void delete(Long id);
}

