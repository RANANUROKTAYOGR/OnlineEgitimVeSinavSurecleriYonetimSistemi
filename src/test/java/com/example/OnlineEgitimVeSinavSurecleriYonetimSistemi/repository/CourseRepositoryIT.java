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

    @Test
    public void findAll_returnsAllCourses() {
        courseRepository.deleteAll();
        Course c1 = Course.builder().title("Course1").description("Desc1").build();
        Course c2 = Course.builder().title("Course2").description("Desc2").build();
        courseRepository.save(c1);
        courseRepository.save(c2);

        var courses = courseRepository.findAll();
        assertThat(courses).hasSize(2);
    }

    @Test
    public void deleteCourse_removesFromDatabase() {
        Course c = Course.builder().title("Delete").description("ToDelete").build();
        c = courseRepository.save(c);
        Long id = c.getId();

        courseRepository.delete(c);

        assertThat(courseRepository.findById(id)).isEmpty();
    }

    @Test
    public void updateCourse_updatesFields() {
        Course c = Course.builder().title("Old").description("OldDesc").build();
        c = courseRepository.save(c);

        c.setTitle("New");
        c.setDescription("NewDesc");
        courseRepository.save(c);

        var updated = courseRepository.findById(c.getId()).orElseThrow();
        assertThat(updated.getTitle()).isEqualTo("New");
        assertThat(updated.getDescription()).isEqualTo("NewDesc");
    }

    @Test
    public void saveCourse_withPrice() {
        Course c = Course.builder()
            .title("Paid Course")
            .description("Complete Description")
            .price(99.99)
            .build();
        c = courseRepository.save(c);

        var saved = courseRepository.findById(c.getId()).orElseThrow();
        assertThat(saved.getPrice()).isEqualTo(99.99);
    }
}
