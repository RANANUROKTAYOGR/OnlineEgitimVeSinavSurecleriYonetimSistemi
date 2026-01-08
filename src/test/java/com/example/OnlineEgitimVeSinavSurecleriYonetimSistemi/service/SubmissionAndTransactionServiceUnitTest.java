package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubmissionAndTransactionServiceUnitTest {
    SubmissionRepository sRepo;
    TransactionRepository tRepo;
    SubmissionServiceImpl sService;
    TransactionServiceImpl tService;

    @BeforeEach
    void setup() {
        sRepo = mock(SubmissionRepository.class);
        tRepo = mock(TransactionRepository.class);
        sService = new SubmissionServiceImpl(sRepo);
        tService = new TransactionServiceImpl(tRepo);
    }

    @Test
    void submission_update_throws_when_missing() {
        when(sRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sService.update(1L, Submission.builder().build()));
    }

    @Test
    void transaction_update_throws_when_missing() {
        when(tRepo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> tService.update(2L, Transaction.builder().build()));
    }

    @Test
    void create_and_list_submission() {
        Submission s = Submission.builder().id(11L).build();
        when(sRepo.save(s)).thenReturn(s);
        when(sRepo.findAll()).thenReturn(List.of(s));

        var saved = sService.create(s);
        assertThat(saved.getId()).isEqualTo(11L);
        assertThat(sService.getAll()).hasSize(1);
    }

    @Test
    void create_and_list_transaction() {
        Transaction t = Transaction.builder().id(12L).build();
        when(tRepo.save(t)).thenReturn(t);
        when(tRepo.findAll()).thenReturn(List.of(t));

        var saved = tService.create(t);
        assertThat(saved.getId()).isEqualTo(12L);
        assertThat(tService.getAll()).hasSize(1);
    }
}
