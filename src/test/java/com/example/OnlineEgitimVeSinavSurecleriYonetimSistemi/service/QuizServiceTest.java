package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizQuestionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QuizServiceTest {
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuizQuestionRepository quizQuestionRepository;

    private QuizServiceImpl quizService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        quizService = new QuizServiceImpl(quizRepository, quizQuestionRepository);
    }

    @Test
    public void calculateQuizScore_allCorrect_returnsFullScore() {
        Quiz quiz = Quiz.builder().id(1L).title("Sample").build();
        QuizQuestion q1 = QuizQuestion.builder().id(10L).questionText("Q1").correctChoice("A").points(2).build();
        QuizQuestion q2 = QuizQuestion.builder().id(11L).questionText("Q2").correctChoice("B").points(3).build();
        quiz.setQuestions(new HashSet<>(Arrays.asList(q1, q2)));

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Map<Long, String> answers = new HashMap<>();
        answers.put(10L, "A");
        answers.put(11L, "B");

        Double score = quizService.calculateQuizScore(1L, answers);
        assertThat(score).isEqualTo(5.0);
    }

    @Test
    public void calculateQuizScore_partialCorrect_returnsPartialScore() {
        Quiz quiz = Quiz.builder().id(2L).title("Sample2").build();
        QuizQuestion q1 = QuizQuestion.builder().id(20L).questionText("Q1").correctChoice("A").points(2).build();
        QuizQuestion q2 = QuizQuestion.builder().id(21L).questionText("Q2").correctChoice("B").points(3).build();
        quiz.setQuestions(new HashSet<>(Arrays.asList(q1, q2)));

        when(quizRepository.findById(2L)).thenReturn(Optional.of(quiz));

        Map<Long, String> answers = new HashMap<>();
        answers.put(20L, "A");
        answers.put(21L, "C");

        Double score = quizService.calculateQuizScore(2L, answers);
        assertThat(score).isEqualTo(2.0);
    }

    @Test
    public void calculateQuizScore_noAnswers_returnsZero() {
        Quiz quiz = Quiz.builder().id(3L).title("Sample3").build();
        quiz.setQuestions(Collections.emptySet());
        when(quizRepository.findById(3L)).thenReturn(Optional.of(quiz));

        Double score = quizService.calculateQuizScore(3L, Collections.emptyMap());
        assertThat(score).isEqualTo(0.0);
    }
}

