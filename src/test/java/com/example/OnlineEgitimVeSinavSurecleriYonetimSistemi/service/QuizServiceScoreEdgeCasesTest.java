package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizQuestionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class QuizServiceScoreEdgeCasesTest {
    QuizRepository quizRepository;
    QuizQuestionRepository quizQuestionRepository;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        quizRepository = mock(QuizRepository.class);
        quizQuestionRepository = mock(QuizQuestionRepository.class);
        service = new QuizServiceImpl(quizRepository, quizQuestionRepository);
    }

    @Test
    void calculateQuizScore_returnsZero_whenAnswersNull() {
        Quiz q = new Quiz(); q.setId(1L);
        when(quizRepository.findById(1L)).thenReturn(Optional.of(q));
        Double score = service.calculateQuizScore(1L, null);
        assertEquals(0.0, score);
    }

    @Test
    void calculateQuizScore_returnsZero_whenQuestionsNull() {
        Quiz q = new Quiz(); q.setId(2L); q.setQuestions(null);
        when(quizRepository.findById(2L)).thenReturn(Optional.of(q));
        Double score = service.calculateQuizScore(2L, Map.of(10L, "A"));
        assertEquals(0.0, score);
    }

    @Test
    void calculateQuizScore_countsPoints_caseInsensitive_and_defaultPoint() {
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(11L); qq1.setCorrectChoice("a"); qq1.setPoints(2);
        QuizQuestion qq2 = new QuizQuestion(); qq2.setId(12L); qq2.setCorrectChoice("B"); qq2.setPoints(null);
        Quiz q = new Quiz(); q.setId(3L); q.setQuestions(new HashSet<>(List.of(qq1, qq2)));
        when(quizRepository.findById(3L)).thenReturn(Optional.of(q));
        Map<Long, String> answers = new HashMap<>();
        answers.put(11L, "A");
        answers.put(12L, "b");
        Double score = service.calculateQuizScore(3L, answers);
        // qq1 gives 2, qq2 gives default 1
        assertEquals(3.0, score);
    }

    @Test
    void calculateQuizScore_ignoresMissingAnswerAndWrongChoice() {
        QuizQuestion qq1 = new QuizQuestion(); qq1.setId(21L); qq1.setCorrectChoice("X"); qq1.setPoints(5);
        QuizQuestion qq2 = new QuizQuestion(); qq2.setId(22L); qq2.setCorrectChoice("Y"); qq2.setPoints(3);
        Quiz q = new Quiz(); q.setId(4L); q.setQuestions(new HashSet<>(List.of(qq1, qq2)));
        when(quizRepository.findById(4L)).thenReturn(Optional.of(q));
        Map<Long, String> answers = Map.of(21L, "wrong");
        Double score = service.calculateQuizScore(4L, answers);
        assertEquals(0.0, score); // none correct
    }
}

