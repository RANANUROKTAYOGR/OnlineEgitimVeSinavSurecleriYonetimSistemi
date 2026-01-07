package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizQuestionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizQuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class QuizQuestionServiceExtendedTest {
    @Mock
    private QuizQuestionRepository quizQuestionRepository;

    @InjectMocks
    private QuizQuestionServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        QuizQuestion question = QuizQuestion.builder()
                .id(1L)
                .questionText("What is Java?")
                .build();
        when(quizQuestionRepository.findById(1L)).thenReturn(Optional.of(question));

        Optional<QuizQuestion> result = service.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getQuestionText()).isEqualTo("What is Java?");
    }

    @Test
    public void testGetAll() {
        List<QuizQuestion> questions = Arrays.asList(
            QuizQuestion.builder().id(1L).questionText("Q1").build(),
            QuizQuestion.builder().id(2L).questionText("Q2").build()
        );
        when(quizQuestionRepository.findAll()).thenReturn(questions);

        List<QuizQuestion> result = service.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void testCreate() {
        QuizQuestion question = QuizQuestion.builder()
                .questionText("New Question")
                .build();
        when(quizQuestionRepository.save(any())).thenReturn(question);

        QuizQuestion result = service.create(question);

        assertThat(result.getQuestionText()).isEqualTo("New Question");
    }

    @Test
    public void testUpdate() {
        QuizQuestion existing = QuizQuestion.builder().id(1L).questionText("Old").build();
        QuizQuestion updated = QuizQuestion.builder().id(1L).questionText("New").build();

        when(quizQuestionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(quizQuestionRepository.save(any())).thenReturn(updated);

        QuizQuestion result = service.update(1L, updated);

        assertThat(result.getQuestionText()).isEqualTo("New");
    }

    @Test
    public void testDelete() {
        doNothing().when(quizQuestionRepository).deleteById(1L);

        service.delete(1L);

        verify(quizQuestionRepository).deleteById(1L);
    }
}

