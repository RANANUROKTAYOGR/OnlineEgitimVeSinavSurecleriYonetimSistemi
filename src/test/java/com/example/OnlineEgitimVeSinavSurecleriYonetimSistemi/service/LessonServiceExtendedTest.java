package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.LessonRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LessonServiceExtendedTest {
    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private LessonServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Lesson lesson = Lesson.builder().id(1L).title("Lesson 1").build();
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        Optional<Lesson> result = service.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Lesson 1");
    }

    @Test
    public void testGetAll() {
        List<Lesson> lessons = Arrays.asList(
            Lesson.builder().id(1L).build(),
            Lesson.builder().id(2L).build(),
            Lesson.builder().id(3L).build()
        );
        when(lessonRepository.findAll()).thenReturn(lessons);

        List<Lesson> result = service.getAll();

        assertThat(result).hasSize(3);
    }

    @Test
    public void testUpdate() {
        Lesson existing = Lesson.builder().id(1L).title("Old").build();
        Lesson updated = Lesson.builder().id(1L).title("New").build();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(lessonRepository.save(any())).thenReturn(updated);

        Lesson result = service.update(1L, updated);

        assertThat(result.getTitle()).isEqualTo("New");
    }

    @Test
    public void testDelete() {
        doNothing().when(lessonRepository).deleteById(1L);

        service.delete(1L);

        verify(lessonRepository).deleteById(1L);
    }
}

