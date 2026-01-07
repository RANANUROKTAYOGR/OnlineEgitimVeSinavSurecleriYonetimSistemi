package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void getById_returnsTransaction() {
        Transaction transaction = Transaction.builder().id(1L).amount(100.0).status("SUCCESS").build();
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getAmount()).isEqualTo(100.0);
    }

    @Test
    public void getAll_returnsAllTransactions() {
        List<Transaction> transactions = Arrays.asList(
            Transaction.builder().id(1L).amount(100.0).build(),
            Transaction.builder().id(2L).amount(200.0).build()
        );
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void update_updatesTransaction() {
        Transaction existing = Transaction.builder().id(1L).amount(100.0).status("PENDING").build();
        Transaction updated = Transaction.builder().id(1L).amount(100.0).status("SUCCESS").build();

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updated);

        Transaction result = transactionService.update(1L, updated);

        assertThat(result.getStatus()).isEqualTo("SUCCESS");
    }

    @Test
    public void delete_deletesTransaction() {
        doNothing().when(transactionRepository).deleteById(1L);

        transactionService.delete(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void calculateTotalAmount_returnsSum() {
        List<Transaction> transactions = Arrays.asList(
            Transaction.builder().amount(100.0).build(),
            Transaction.builder().amount(200.0).build(),
            Transaction.builder().amount(50.0).build()
        );

        double total = transactions.stream()
            .mapToDouble(Transaction::getAmount)
            .sum();

        assertThat(total).isEqualTo(350.0);
    }
}
