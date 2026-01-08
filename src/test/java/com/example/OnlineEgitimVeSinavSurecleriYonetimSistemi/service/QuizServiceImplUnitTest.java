package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceImplUnitTest {
    QuizRepository quizRepository;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        quizRepository = mock(QuizRepository.class);
        service = new QuizServiceImpl(quizRepository);
    }

    @Test
    void calculateQuizScore_throws_when_quiz_not_found() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.calculateQuizScore(1L, Map.of(1L, "A")));
        assertTrue(ex.getMessage().toLowerCase().contains("quiz not found"));
    }

    @Test
    void calculateQuizScore_returns_zero_when_answers_null_or_empty() {
        Quiz quiz = Quiz.builder().id(2L).questions(Set.of()).build();
        when(quizRepository.findById(2L)).thenReturn(Optional.of(quiz));
        assertEquals(0.0, service.calculateQuizScore(2L, null));
        assertEquals(0.0, service.calculateQuizScore(2L, Map.of()));
    }

    @Test
    void calculateQuizScore_returns_zero_when_questions_null_or_empty() {
        Quiz quiz = Quiz.builder().id(3L).questions(null).build();
        when(quizRepository.findById(3L)).thenReturn(Optional.of(quiz));
        assertEquals(0.0, service.calculateQuizScore(3L, Map.of(10L, "a")));

        Quiz quiz2 = Quiz.builder().id(4L).questions(Set.of()).build();
        when(quizRepository.findById(4L)).thenReturn(Optional.of(quiz2));
        assertEquals(0.0, service.calculateQuizScore(4L, Map.of(10L, "a")));
    }

    @Test
    void calculateQuizScore_counts_points_and_ignores_case() {
        QuizQuestion q1 = QuizQuestion.builder().id(11L).correctChoice("A").points(2).build();
        QuizQuestion q2 = QuizQuestion.builder().id(12L).correctChoice("b").points(null).build();
        Quiz quiz = Quiz.builder().id(5L).questions(Set.of(q1, q2)).build();
        when(quizRepository.findById(5L)).thenReturn(Optional.of(quiz));

        double score = service.calculateQuizScore(5L, Map.of(11L, "a", 12L, "B"));
        // q1 => 2 points, q2 => default 1 point
        assertEquals(3.0, score);
    }
}

