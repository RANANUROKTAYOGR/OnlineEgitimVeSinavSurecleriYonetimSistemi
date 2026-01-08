package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubmissionServiceImplUnitTest {
    SubmissionRepository repository;
    SubmissionServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(SubmissionRepository.class);
        service = new SubmissionServiceImpl(repository);
    }

    @Test
    void create_delegates_to_repository() {
        Submission s = Submission.builder().id(1L).build();
        when(repository.save(s)).thenReturn(s);
        assertSame(s, service.create(s));
        verify(repository).save(s);
    }

    @Test
    void update_throws_when_not_found() {
        when(repository.findById(5L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(5L, Submission.builder().id(5L).build()));
        assertTrue(ex.getMessage().toLowerCase().contains("submission not found"));
    }

    @Test
    void delete_calls_repository() {
        doNothing().when(repository).deleteById(7L);
        service.delete(7L);
        verify(repository).deleteById(7L);
    }

    @Test
    void getAll_and_getById_delegates() {
        Submission s = Submission.builder().id(2L).build();
        when(repository.findAll()).thenReturn(List.of(s));
        when(repository.findById(2L)).thenReturn(Optional.of(s));
        assertEquals(1, service.getAll().size());
        assertTrue(service.getById(2L).isPresent());
    }
}

