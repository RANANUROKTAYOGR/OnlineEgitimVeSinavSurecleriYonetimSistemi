package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizQuestionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.QuizRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.QuizService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository; // may be null in some tests

    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = null;
    }

    // Additional constructor used by Spring
    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, QuizQuestionRepository quizQuestionRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @Override
    public Quiz create(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Optional<Quiz> getById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz findById(Long id) {
        return quizRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        return quizRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(quiz, existing, "id");
            return quizRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    @Override
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public Double calculateQuizScore(Long quizId, Map<Long, String> answers) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));
        double total = 0.0;
        if (answers == null || answers.isEmpty()) return total;
        // safer: handle null questions set
        var questions = quiz.getQuestions();
        if (questions == null || questions.isEmpty()) return total;
        for (var q : questions) {
            String selected = answers.get(q.getId());
            // call equalsIgnoreCase on selected (non-null) so we don't need to null-check the stored correctChoice
            if (selected != null && selected.equalsIgnoreCase(q.getCorrectChoice())) {
                total += (q.getPoints() != null ? q.getPoints() : 1);
            }
        }
        return total;
    }
}
