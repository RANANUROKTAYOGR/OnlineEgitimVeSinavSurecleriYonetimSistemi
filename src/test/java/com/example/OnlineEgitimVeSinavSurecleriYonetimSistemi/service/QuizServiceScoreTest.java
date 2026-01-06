package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QuizServiceScoreTest {
    @Mock
    private QuizRepository quizRepository;

    private QuizServiceImpl quizService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        quizService = new QuizServiceImpl(quizRepository);
    }

    @Test
    public void calculateQuizScore_allCorrect_returnsSum() {
        QuizQuestion q1 = QuizQuestion.builder().id(1L).points(2).correctChoice("A").build();
        QuizQuestion q2 = QuizQuestion.builder().id(2L).points(3).correctChoice("B").build();
        Set<QuizQuestion> questions = new HashSet<>();
        questions.add(q1); questions.add(q2);
        Quiz quiz = Quiz.builder().id(10L).questions(questions).build();

        when(quizRepository.findById(10L)).thenReturn(java.util.Optional.of(quiz));

        Map<Long, String> answers = new HashMap<>();
        answers.put(1L, "A");
        answers.put(2L, "B");

        Double score = quizService.calculateQuizScore(10L, answers);
        assertThat(score).isEqualTo(5.0);
    }

    @Test
    public void calculateQuizScore_someWrong_returnsPartial() {
        QuizQuestion q1 = QuizQuestion.builder().id(1L).points(2).correctChoice("A").build();
        QuizQuestion q2 = QuizQuestion.builder().id(2L).points(3).correctChoice("B").build();
        Set<QuizQuestion> questions = new HashSet<>();
        questions.add(q1); questions.add(q2);
        Quiz quiz = Quiz.builder().id(11L).questions(questions).build();

        when(quizRepository.findById(11L)).thenReturn(java.util.Optional.of(quiz));

        Map<Long, String> answers = new HashMap<>();
        answers.put(1L, "A");
        answers.put(2L, "C");

        Double score = quizService.calculateQuizScore(11L, answers);
        assertThat(score).isEqualTo(2.0);
    }

    @Test
    public void calculateQuizScore_noAnswers_returnsZero() {
        Quiz quiz = Quiz.builder().id(12L).questions(new HashSet<>()).build();
        when(quizRepository.findById(12L)).thenReturn(java.util.Optional.of(quiz));
        Double score = quizService.calculateQuizScore(12L, null);
        assertThat(score).isEqualTo(0.0);
    }
}

