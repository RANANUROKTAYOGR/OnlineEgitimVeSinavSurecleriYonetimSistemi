package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.SubmissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public Submission create(Submission submission) {
        return submissionRepository.save(submission);
    }

    @Override
    public Optional<Submission> getById(Long id) {
        return submissionRepository.findById(id);
    }

    @Override
    public List<Submission> getAll() {
        return submissionRepository.findAll();
    }

    @Override
    public Submission update(Long id, Submission submission) {
        return submissionRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(submission, existing, "id");
            return submissionRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Submission not found"));
    }

    @Override
    public void delete(Long id) {
        submissionRepository.deleteById(id);
    }
}

