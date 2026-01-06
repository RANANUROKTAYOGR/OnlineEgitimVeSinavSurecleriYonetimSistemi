package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.LessonRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class LessonServiceTest {
    @Mock
    private LessonRepository lessonRepository;
    private LessonServiceImpl lessonService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        lessonService = new LessonServiceImpl(lessonRepository);
    }

    @Test
    public void createLesson_savesAndReturns(){
        Lesson l = Lesson.builder().title("L1").content("c").position(1).build();
        when(lessonRepository.save(l)).thenReturn(Lesson.builder().id(1L).title("L1").build());
        var saved = lessonService.create(l);
        assertThat(saved.getId()).isEqualTo(1L);
    }
}

