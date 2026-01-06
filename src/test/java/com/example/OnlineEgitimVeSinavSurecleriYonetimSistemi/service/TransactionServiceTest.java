package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    private TransactionServiceImpl transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    public void createTransaction_saves() {
        Transaction t = Transaction.builder().amount(20.0).status("SUCCESS").createdAt(LocalDateTime.now()).build();
        when(transactionRepository.save(t)).thenReturn(Transaction.builder().id(2L).amount(20.0).build());
        var saved = transactionService.create(t);
        assertThat(saved.getId()).isEqualTo(2L);
    }
}
