package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class QuizRepositoryIT {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Test
    public void saveQuizWithQuestions_shouldPersistAndRetrieve() {
        Quiz quiz = Quiz.builder().title("IT Quiz").totalPoints(5).build();
        quiz = quizRepository.save(quiz);

        QuizQuestion q = QuizQuestion.builder().questionText("IT Q1").choiceA("A").choiceB("B").choiceC("C").choiceD("D").correctChoice("A").points(1).quiz(quiz).build();
        quizQuestionRepository.save(q);

        // refresh
        var fetched = quizRepository.findById(quiz.getId()).orElse(null);
        assertThat(fetched).isNotNull();
        // questions may be lazy; size check via repository query
        var questions = quizQuestionRepository.findAll();
        assertThat(questions).isNotEmpty();
        assertThat(questions.iterator().next().getQuiz().getId()).isEqualTo(quiz.getId());
    }
}
