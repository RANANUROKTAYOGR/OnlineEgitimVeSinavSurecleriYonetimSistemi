package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionUpdateAndDeleteTest {
    TransactionRepository repo;
    TransactionServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(TransactionRepository.class);
        service = new TransactionServiceImpl(repo);
    }

    @Test
    void update_existing_transaction_copiesProperties() {
        Transaction existing = new Transaction(); existing.setId(1L); existing.setAmount(10.0);
        Transaction updated = new Transaction(); updated.setAmount(99.0);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        Transaction res = service.update(1L, updated);
        assertEquals(99.0, res.getAmount());
    }

    @Test
    void update_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(2L, new Transaction()));
    }

    @Test
    void delete_callsRepository() {
        doNothing().when(repo).deleteById(3L);
        service.delete(3L);
        verify(repo, times(1)).deleteById(3L);
    }
}

