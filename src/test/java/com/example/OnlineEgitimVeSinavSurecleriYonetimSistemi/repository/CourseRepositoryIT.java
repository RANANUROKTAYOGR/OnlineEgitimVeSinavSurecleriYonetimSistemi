package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CourseRepositoryIT {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void saveCourse_fetchesById() {
        Course c = Course.builder().title("CRS").description("d").build();
        c = courseRepository.save(c);
        var got = courseRepository.findById(c.getId()).orElse(null);
        assertThat(got).isNotNull();
        assertThat(got.getTitle()).isEqualTo("CRS");
    }
}
