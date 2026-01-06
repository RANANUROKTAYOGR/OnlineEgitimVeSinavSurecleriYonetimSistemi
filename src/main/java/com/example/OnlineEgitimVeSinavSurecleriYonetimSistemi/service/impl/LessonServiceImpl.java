package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.LessonRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.LessonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public Lesson create(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson update(Long id, Lesson lesson) {
        return lessonRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(lesson, existing, "id");
            return lessonRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    @Override
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}

