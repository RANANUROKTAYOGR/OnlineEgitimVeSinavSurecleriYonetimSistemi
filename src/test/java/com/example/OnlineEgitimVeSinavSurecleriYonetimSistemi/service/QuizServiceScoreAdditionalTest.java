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

public class QuizServiceScoreAdditionalTest {
    QuizRepository quizRepository;
    QuizServiceImpl service;

    @BeforeEach
    void setup() {
        quizRepository = mock(QuizRepository.class);
        service = new QuizServiceImpl(quizRepository);
    }

    @Test
    void calculate_score_with_null_answers_returns_zero() {
        Quiz q = new Quiz(); q.setId(1L); q.setQuestions(new HashSet<>());
        when(quizRepository.findById(1L)).thenReturn(Optional.of(q));
        double score = service.calculateQuizScore(1L, null);
        assertEquals(0.0, score);
    }

    @Test
    void calculate_score_with_empty_answers_returns_zero_even_if_questions_present() {
        QuizQuestion qq = QuizQuestion.builder().id(10L).correctChoice("A").points(2).build();
        Quiz quiz = new Quiz(); quiz.setId(2L); quiz.setQuestions(new HashSet<>(List.of(qq)));
        when(quizRepository.findById(2L)).thenReturn(Optional.of(quiz));
        double score = service.calculateQuizScore(2L, Collections.emptyMap());
        assertEquals(0.0, score);
    }

    @Test
    void calculate_score_counts_points_and_ignores_case_and_missing_points() {
        QuizQuestion q1 = QuizQuestion.builder().id(11L).correctChoice("a").points(3).build();
        QuizQuestion q2 = QuizQuestion.builder().id(12L).correctChoice("B").points(null).build(); // null -> default 1
        Quiz quiz = new Quiz(); quiz.setId(3L); quiz.setQuestions(new HashSet<>(List.of(q1, q2)));
        when(quizRepository.findById(3L)).thenReturn(Optional.of(quiz));

        Map<Long, String> answers = new HashMap<>();
        answers.put(11L, "A"); // match ignoring case
        answers.put(12L, "b"); // match ignoring case

        double score = service.calculateQuizScore(3L, answers);
        assertEquals(4.0, score); // 3 + 1
    }

    @Test
    void calculate_score_skips_null_question_list_on_quiz() {
        Quiz quiz = new Quiz(); quiz.setId(4L); quiz.setQuestions(null);
        when(quizRepository.findById(4L)).thenReturn(Optional.of(quiz));
        double score = service.calculateQuizScore(4L, Map.of(1L, "A"));
        assertEquals(0.0, score);
    }

    @Test
    void calculate_score_throws_when_quiz_missing() {
        when(quizRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.calculateQuizScore(99L, Map.of()));
    }
}
