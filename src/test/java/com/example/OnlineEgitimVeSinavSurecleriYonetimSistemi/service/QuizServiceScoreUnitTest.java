package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class QuizServiceScoreUnitTest {

    @Test
    void calculateQuizScore_allCorrect_withPoints() {
        QuizRepository repo = Mockito.mock(QuizRepository.class);
        QuizServiceImpl service = new QuizServiceImpl(repo);

        Quiz q = new Quiz();
        q.setId(1L);
        QuizQuestion qq1 = new QuizQuestion();
        qq1.setId(11L);
        qq1.setCorrectChoice("A");
        qq1.setPoints(2);
        QuizQuestion qq2 = new QuizQuestion();
        qq2.setId(12L);
        qq2.setCorrectChoice("b");
        qq2.setPoints(3);
        q.setQuestions(Set.of(qq1, qq2));

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(q));

        Map<Long, String> answers = new HashMap<>();
        answers.put(11L, "a"); // case-insensitive
        answers.put(12L, "B");

        double score = service.calculateQuizScore(1L, answers);
        assertThat(score).isEqualTo(5.0);
    }

    @Test
    void calculateQuizScore_partialAndNulls() {
        QuizRepository repo = Mockito.mock(QuizRepository.class);
        QuizServiceImpl service = new QuizServiceImpl(repo);

        Quiz q = new Quiz();
        q.setId(2L);
        QuizQuestion qq1 = new QuizQuestion();
        qq1.setId(21L);
        qq1.setCorrectChoice("C");
        qq1.setPoints(null); // defaults to 1
        QuizQuestion qq2 = new QuizQuestion();
        qq2.setId(22L);
        qq2.setCorrectChoice(null);
        qq2.setPoints(4);
        q.setQuestions(Set.of(qq1, qq2));

        Mockito.when(repo.findById(2L)).thenReturn(Optional.of(q));

        Map<Long, String> answers = new HashMap<>();
        answers.put(21L, "c");
        answers.put(22L, "whatever");

        double score = service.calculateQuizScore(2L, answers);
        assertThat(score).isEqualTo(1.0);
    }

    @Test
    void calculateQuizScore_noQuestionsOrAnswers_returnsZero() {
        QuizRepository repo = Mockito.mock(QuizRepository.class);
        QuizServiceImpl service = new QuizServiceImpl(repo);

        Quiz q = new Quiz();
        q.setId(3L);
        q.setQuestions(Collections.emptySet());

        Mockito.when(repo.findById(3L)).thenReturn(Optional.of(q));

        double score = service.calculateQuizScore(3L, Collections.emptyMap());
        assertThat(score).isEqualTo(0.0);
    }
}
