package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class QuizQuestionRepositoryIT {
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Test
    public void saveQuestion_andRetrieve() {
        QuizQuestion q = QuizQuestion.builder().questionText("q").correctChoice("A").points(1).build();
        q = quizQuestionRepository.save(q);
        var got = quizQuestionRepository.findById(q.getId()).orElse(null);
        assertThat(got).isNotNull();
    }
}
