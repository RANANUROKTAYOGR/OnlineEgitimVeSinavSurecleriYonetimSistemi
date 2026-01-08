package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class QuizServiceCrudUnitTest {
    QuizRepository repo;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(QuizRepository.class);
        service = new QuizServiceImpl(repo);
    }

    @Test
    void findById_throws_when_missing() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(1L));
    }

    @Test
    void update_throws_when_missing() {
        when(repo.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(10L, Quiz.builder().build()));
    }

    @Test
    void delete_calls_repository() {
        service.delete(5L);
        verify(repo, times(1)).deleteById(5L);
    }

    @Test
    void create_and_list() {
        Quiz q = Quiz.builder().id(7L).title("T").build();
        when(repo.save(q)).thenReturn(q);
        when(repo.findAll()).thenReturn(List.of(q));
        var c = service.create(q);
        assertThat(c.getId()).isEqualTo(7L);
        var all = service.getAll();
        assertThat(all).hasSize(1);
    }
}

