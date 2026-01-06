package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.QuizQuestion;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizQuestionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.QuizQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionServiceImpl(QuizQuestionRepository quizQuestionRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @Override
    public QuizQuestion create(QuizQuestion quizQuestion) {
        return quizQuestionRepository.save(quizQuestion);
    }

    @Override
    public Optional<QuizQuestion> getById(Long id) {
        return quizQuestionRepository.findById(id);
    }

    @Override
    public List<QuizQuestion> getAll() {
        return quizQuestionRepository.findAll();
    }

    @Override
    public QuizQuestion update(Long id, QuizQuestion quizQuestion) {
        return quizQuestionRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(quizQuestion, existing, "id");
            return quizQuestionRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("QuizQuestion not found"));
    }

    @Override
    public void delete(Long id) {
        quizQuestionRepository.deleteById(id);
    }
}

