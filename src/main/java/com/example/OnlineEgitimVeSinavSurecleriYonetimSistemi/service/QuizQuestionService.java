package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;

import java.util.List;
import java.util.Optional;

public interface QuizQuestionService {
    QuizQuestion create(QuizQuestion quizQuestion);
    Optional<QuizQuestion> getById(Long id);
    List<QuizQuestion> getAll();
    QuizQuestion update(Long id, QuizQuestion quizQuestion);
    void delete(Long id);
}

