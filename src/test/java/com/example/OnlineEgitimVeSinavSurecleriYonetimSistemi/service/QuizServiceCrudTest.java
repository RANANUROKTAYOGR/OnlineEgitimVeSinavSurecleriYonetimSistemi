package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceCrudTest {
    QuizRepository repo;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(QuizRepository.class);
        service = new QuizServiceImpl(repo);
    }

    @Test
    void create_get_update_delete_flow() {
        Quiz q = new Quiz(); q.setId(1L); q.setTitle("T");
        when(repo.save(q)).thenReturn(q);
        when(repo.findById(1L)).thenReturn(Optional.of(q));
        when(repo.findAll()).thenReturn(List.of(q));
        Quiz created = service.create(q);
        assertEquals("T", created.getTitle());
        assertEquals(q, service.findById(1L));
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(2L));
    }
}

