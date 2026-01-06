package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizQuestionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.QuizQuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QuizQuestionServiceTest {
    @Mock
    private QuizQuestionRepository quizQuestionRepository;
    private QuizQuestionServiceImpl service;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        service = new QuizQuestionServiceImpl(quizQuestionRepository);
    }

    @Test
    public void createQuestion_savesAndReturns(){
        QuizQuestion q = QuizQuestion.builder().questionText("q").choiceA("A").correctChoice("A").points(1).build();
        when(quizQuestionRepository.save(q)).thenReturn(QuizQuestion.builder().id(1L).questionText("q").build());
        var saved = service.create(q);
        assertThat(saved.getId()).isEqualTo(1L);
    }
}

