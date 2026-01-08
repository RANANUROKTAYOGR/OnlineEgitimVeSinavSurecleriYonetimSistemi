package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizScoreAndEdgeCasesTest {
    QuizRepository quizRepository;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        quizRepository = mock(QuizRepository.class);
        service = new QuizServiceImpl(quizRepository);
    }

    @Test
    void calculateScore_allCorrect_withPoints() {
        Quiz q = new Quiz(); q.setId(10L);
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(1L); qq1.setCorrectChoice("A"); qq1.setPoints(2);
        QuizQuestion qq2 = new QuizQuestion(); qq2.setId(2L); qq2.setCorrectChoice("b"); qq2.setPoints(3);
        q.setQuestions(new HashSet<>(List.of(qq1, qq2)));
        when(quizRepository.findById(10L)).thenReturn(Optional.of(q));
        Map<Long,String> answers = Map.of(1L, "a", 2L, "B");
        double score = service.calculateQuizScore(10L, answers);
        assertEquals(5.0, score);
    }

    @Test
    void calculateScore_someWrong_and_nullAnswers() {
        Quiz q = new Quiz(); q.setId(11L);
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(3L); qq1.setCorrectChoice("C"); qq1.setPoints(null);
        QuizQuestion qq2 = new QuizQuestion(); qq2.setId(4L); qq2.setCorrectChoice("D"); qq2.setPoints(4);
        q.setQuestions(new HashSet<>(List.of(qq1, qq2)));
        when(quizRepository.findById(11L)).thenReturn(Optional.of(q));
        java.util.Map<Long,String> answers = new java.util.HashMap<>();
        answers.put(3L, "x");
        answers.put(4L, null);
        double score = service.calculateQuizScore(11L, answers);
        // qq1 wrong -> 0, qq2 null selected -> 0
        assertEquals(0.0, score);
    }

    @Test
    void calculateScore_noAnswers_or_noQuestions_returnsZero() {
        Quiz q = new Quiz(); q.setId(12L);
        q.setQuestions(Collections.emptySet());
        when(quizRepository.findById(12L)).thenReturn(Optional.of(q));
        assertEquals(0.0, service.calculateQuizScore(12L, Collections.emptyMap()));
        assertEquals(0.0, service.calculateQuizScore(12L, null));
    }

    @Test
    void findById_notFound_throws() {
        when(quizRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findById(99L));
        assertTrue(ex.getMessage().contains("Quiz not found"));
    }
}
