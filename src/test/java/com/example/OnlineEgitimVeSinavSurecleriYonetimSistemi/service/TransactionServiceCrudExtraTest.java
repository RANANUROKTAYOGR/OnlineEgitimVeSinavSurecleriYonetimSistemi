package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceCrudExtraTest {
    TransactionRepository repository;
    TransactionServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(TransactionRepository.class);
        service = new TransactionServiceImpl(repository);
    }

    @Test
    void update_throws_whenMissing() {
        Transaction t = new Transaction(); t.setId(5L);
        when(repository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(5L, t));
    }

    @Test
    void delete_calls_repo() {
        doNothing().when(repository).deleteById(6L);
        service.delete(6L);
        verify(repository).deleteById(6L);
    }
}

