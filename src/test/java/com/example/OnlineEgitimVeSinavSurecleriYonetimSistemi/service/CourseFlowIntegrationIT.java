package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CourseFlowIntegrationIT {
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void createCourse_persisted() {
        Course c = Course.builder().title("integ").description("d").build();
        c = courseRepository.save(c);
        assertThat(courseRepository.findById(c.getId())).isPresent();
    }
}

