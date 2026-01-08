package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizScoreCalculationTest {
    QuizRepository quizRepository;
    QuizServiceImpl quizService;

    @BeforeEach
    void setup() {
        quizRepository = mock(QuizRepository.class);
        quizService = new QuizServiceImpl(quizRepository);
    }

    @Test
    void returnsZeroWhenAnswersNullOrEmpty_or_noQuestions() {
        Quiz q = new Quiz(); q.setId(1L);
        q.setQuestions(Set.of());
        when(quizRepository.findById(1L)).thenReturn(Optional.of(q));
        assertEquals(0.0, quizService.calculateQuizScore(1L, null));
        assertEquals(0.0, quizService.calculateQuizScore(1L, Map.of()));
    }

    @Test
    void calculatesPoints_respectingNullPointsAndCaseInsensitiveMatching() {
        Quiz q = new Quiz(); q.setId(2L);
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(100L); qq1.setCorrectChoice("A"); qq1.setPoints(2);
        QuizQuestion qq2 = new QuizQuestion(); qq2.setId(101L); qq2.setCorrectChoice("b"); qq2.setPoints(null);
        q.setQuestions(Set.of(qq1, qq2));
        when(quizRepository.findById(2L)).thenReturn(Optional.of(q));
        Map<Long, String> answers = new HashMap<>();
        answers.put(100L, "a");
        answers.put(101L, "B");
        double total = quizService.calculateQuizScore(2L, answers);
        // qq1 -> 2 points, qq2 -> default 1 point
        assertEquals(3.0, total);
    }
}
