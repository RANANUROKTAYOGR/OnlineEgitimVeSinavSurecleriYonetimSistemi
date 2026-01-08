package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceCrudExtraTest2 {
    TransactionRepository repository;
    TransactionServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(TransactionRepository.class);
        service = new TransactionServiceImpl(repository);
    }

    @Test
    void create_and_getAll() {
        Transaction t = new Transaction(); t.setId(1L); t.setAmount(10.0);
        when(repository.save(t)).thenReturn(t);
        when(repository.findAll()).thenReturn(List.of(t));
        Transaction c = service.create(t);
        assertEquals(10.0, c.getAmount());
        assertEquals(1, service.getAll().size());
    }

    @Test
    void update_throws_when_missing_id() {
        Transaction t = new Transaction(); t.setId(99L);
        when(repository.findById(99L)).thenReturn(java.util.Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(99L, t));
    }
}

