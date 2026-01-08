package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubmissionAndTransactionServiceTests {
    SubmissionRepository submissionRepository;
    SubmissionServiceImpl submissionService;

    TransactionRepository transactionRepository;
    TransactionServiceImpl transactionService;

    @BeforeEach
    void setup() {
        submissionRepository = mock(SubmissionRepository.class);
        submissionService = new SubmissionServiceImpl(submissionRepository);

        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void updateSubmission_whenExists_copyPropertiesAndSave() {
        Submission existing = new Submission(); existing.setId(1L); existing.setScore(5.0);
        Submission update = new Submission(); update.setScore(9.0);
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(submissionRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Submission result = submissionService.update(1L, update);
        assertEquals(9.0, result.getScore());
        verify(submissionRepository).save(existing);
    }

    @Test
    void updateSubmission_whenNotFound_throw() {
        when(submissionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> submissionService.update(2L, new Submission()));
    }

    @Test
    void updateTransaction_whenNotFound_throw() {
        when(transactionRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> transactionService.update(5L, new Transaction()));
    }
}
