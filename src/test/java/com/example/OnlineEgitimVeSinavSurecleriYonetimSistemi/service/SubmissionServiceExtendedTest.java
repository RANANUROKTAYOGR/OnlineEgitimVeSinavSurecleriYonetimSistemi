package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
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

public class SubmissionServiceExtendedTest {
    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Submission submission = Submission.builder().id(1L).score(85.0).build();
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        Optional<Submission> result = service.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getScore()).isEqualTo(85.0);
    }

    @Test
    public void testGetAll() {
        List<Submission> submissions = Arrays.asList(
            Submission.builder().id(1L).score(85.0).build(),
            Submission.builder().id(2L).score(90.0).build()
        );
        when(submissionRepository.findAll()).thenReturn(submissions);

        List<Submission> result = service.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void testCreate() {
        Submission submission = Submission.builder().score(95.0).build();
        when(submissionRepository.save(any())).thenReturn(submission);

        Submission result = service.create(submission);

        assertThat(result.getScore()).isEqualTo(95.0);
    }

    @Test
    public void testUpdate() {
        Submission existing = Submission.builder().id(1L).score(80.0).build();
        Submission updated = Submission.builder().id(1L).score(90.0).build();

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(submissionRepository.save(any())).thenReturn(updated);

        Submission result = service.update(1L, updated);

        assertThat(result.getScore()).isEqualTo(90.0);
    }

    @Test
    public void testDelete() {
        doNothing().when(submissionRepository).deleteById(1L);

        service.delete(1L);

        verify(submissionRepository).deleteById(1L);
    }

    @Test
    public void testCalculateScore_AllCorrect() {
        int totalQuestions = 10;
        int correctAnswers = 10;

        double score = (double) correctAnswers / totalQuestions * 100;

        assertThat(score).isEqualTo(100.0);
    }

    @Test
    public void testCalculateScore_HalfCorrect() {
        int totalQuestions = 10;
        int correctAnswers = 5;

        double score = (double) correctAnswers / totalQuestions * 100;

        assertThat(score).isEqualTo(50.0);
    }

    @Test
    public void testCalculateScore_ZeroCorrect() {
        int totalQuestions = 10;
        int correctAnswers = 0;

        double score = (double) correctAnswers / totalQuestions * 100;

        assertThat(score).isEqualTo(0.0);
    }
}

