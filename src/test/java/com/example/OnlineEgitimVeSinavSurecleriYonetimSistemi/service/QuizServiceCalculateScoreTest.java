package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceCalculateScoreTest {
    QuizRepository quizRepository;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        quizRepository = mock(QuizRepository.class);
        service = new QuizServiceImpl(quizRepository);
    }

    @Test
    void whenQuizNotFound_thenThrow() {
        when(quizRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.calculateQuizScore(1L, Map.of(1L, "A")));
    }

    @Test
    void whenAnswersNullOrEmpty_thenZero() {
        Quiz q = new Quiz(); q.setId(2L);
        when(quizRepository.findById(2L)).thenReturn(Optional.of(q));
        assertEquals(0.0, service.calculateQuizScore(2L, null));
        assertEquals(0.0, service.calculateQuizScore(2L, Map.of()));
    }

    @Test
    void whenQuestionsNull_thenZero() {
        Quiz q = new Quiz(); q.setId(3L); q.setQuestions(null);
        when(quizRepository.findById(3L)).thenReturn(Optional.of(q));
        assertEquals(0.0, service.calculateQuizScore(3L, Map.of(10L, "A")));
    }

    @Test
    void whenAnswersMatchPointsAreSummed_caseInsensitive_and_defaultPoint() {
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(11L); qq1.setCorrectChoice("a"); qq1.setPoints(2);
        QuizQuestion qq2 = new QuizQuestion(); qq2.setId(12L); qq2.setCorrectChoice("B"); // default points = 1
        Quiz q = new Quiz(); q.setId(4L); q.setQuestions(new HashSet<>(List.of(qq1, qq2)));
        when(quizRepository.findById(4L)).thenReturn(Optional.of(q));

        Map<Long, String> answers = new HashMap<>();
        answers.put(11L, "A"); // match, 2 points
        answers.put(12L, "b"); // match case-insensitive, 1 point
        double score = service.calculateQuizScore(4L, answers);
        assertEquals(3.0, score);
    }

    @Test
    void whenSelectedNull_thenIgnored() {
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(21L); qq1.setCorrectChoice("C"); qq1.setPoints(3);
        Quiz q = new Quiz(); q.setId(5L); q.setQuestions(new HashSet<>(List.of(qq1)));
        when(quizRepository.findById(5L)).thenReturn(Optional.of(q));
        Map<Long, String> answers = new HashMap<>();
        answers.put(21L, null);
        assertEquals(0.0, service.calculateQuizScore(5L, answers));
    }
}

