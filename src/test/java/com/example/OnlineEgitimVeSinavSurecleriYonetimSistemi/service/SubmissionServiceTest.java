package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class SubmissionServiceTest {
    @Mock
    private SubmissionRepository submissionRepository;

    private SubmissionServiceImpl submissionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        submissionService = new SubmissionServiceImpl(submissionRepository);
    }

    @Test
    public void createSubmission_savesAndReturns() {
        Submission s = Submission.builder().score(4.0).build();
        when(submissionRepository.save(s)).thenReturn(Submission.builder().id(3L).score(4.0).build());
        var saved = submissionService.create(s);
        assertThat(saved.getId()).isEqualTo(3L);
    }
}

