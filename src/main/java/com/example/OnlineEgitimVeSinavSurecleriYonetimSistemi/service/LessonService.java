package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    Lesson create(Lesson lesson);
    Optional<Lesson> getById(Long id);
    List<Lesson> getAll();
    Lesson update(Long id, Lesson lesson);
    void delete(Long id);
}

