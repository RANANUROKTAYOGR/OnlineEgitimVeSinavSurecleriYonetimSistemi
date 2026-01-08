package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceImplUnitTest {
    TransactionRepository repository;
    TransactionServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(TransactionRepository.class);
        service = new TransactionServiceImpl(repository);
    }

    @Test
    void create_and_getbyId_and_getAll() {
        Transaction t = Transaction.builder().id(1L).amount(100.0).build();
        when(repository.save(t)).thenReturn(t);
        when(repository.findById(1L)).thenReturn(Optional.of(t));
        when(repository.findAll()).thenReturn(List.of(t));

        assertSame(t, service.create(t));
        assertTrue(service.getById(1L).isPresent());
        assertEquals(1, service.getAll().size());
    }

    @Test
    void update_throws_when_not_found() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(99L, Transaction.builder().id(99L).build()));
        assertTrue(ex.getMessage().toLowerCase().contains("transaction not found"));
    }

    @Test
    void delete_calls_repository() {
        doNothing().when(repository).deleteById(4L);
        service.delete(4L);
        verify(repository).deleteById(4L);
    }
}

