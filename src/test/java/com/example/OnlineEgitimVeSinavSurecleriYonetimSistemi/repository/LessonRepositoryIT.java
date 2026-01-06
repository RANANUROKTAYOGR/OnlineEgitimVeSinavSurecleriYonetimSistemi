package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class LessonRepositoryIT {
    @Autowired
    private LessonRepository lessonRepository;

    @Test
    public void saveLesson_andFetch() {
        Lesson l = Lesson.builder().title("L").content("c").position(1).build();
        l = lessonRepository.save(l);
        var got = lessonRepository.findById(l.getId()).orElse(null);
        assertThat(got).isNotNull();
    }
}
