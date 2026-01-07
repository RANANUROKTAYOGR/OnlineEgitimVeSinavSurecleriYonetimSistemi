package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    Quiz create(Quiz quiz);
    Optional<Quiz> getById(Long id);
    Quiz findById(Long id);
    List<Quiz> getAll();
    List<Quiz> findAll();
    Quiz update(Long id, Quiz quiz);
    void delete(Long id);

    // Hesaplama metodları
    /**
     * Calculate total score for a submission answers map (questionId -> chosenChoice)
     * returns total points scored
     */
    Double calculateQuizScore(Long quizId, java.util.Map<Long, String> answers);
}
