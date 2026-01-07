package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EnrollmentRepositoryIT {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void saveEnrollment_andFind() {
        Enrollment e = Enrollment.builder().active(true).build();
        e = enrollmentRepository.save(e);
        var got = enrollmentRepository.findById(e.getId()).orElse(null);
        assertThat(got).isNotNull();
    }

    @Test
    public void saveEnrollment_withUserAndCourse() {
        User user = User.builder().username("student").email("s@test.com").password("pass").build();
        user = userRepository.save(user);

        Course course = Course.builder().title("Test Course").description("Desc").build();
        course = courseRepository.save(course);

        Enrollment enrollment = Enrollment.builder()
            .user(user)
            .course(course)
            .active(true)
            .completedLessons(0)
            .build();
        enrollment = enrollmentRepository.save(enrollment);

        var saved = enrollmentRepository.findById(enrollment.getId()).orElseThrow();
        assertThat(saved.getUser().getUsername()).isEqualTo("student");
        assertThat(saved.getCourse().getTitle()).isEqualTo("Test Course");
        assertThat(saved.getActive()).isTrue();
    }

    @Test
    public void updateEnrollment_completedLessons() {
        Enrollment enrollment = Enrollment.builder().active(true).completedLessons(0).build();
        enrollment = enrollmentRepository.save(enrollment);

        enrollment.setCompletedLessons(5);
        enrollmentRepository.save(enrollment);

        var updated = enrollmentRepository.findById(enrollment.getId()).orElseThrow();
        assertThat(updated.getCompletedLessons()).isEqualTo(5);
    }

    @Test
    public void deleteEnrollment_removesFromDatabase() {
        Enrollment enrollment = Enrollment.builder().active(true).build();
        enrollment = enrollmentRepository.save(enrollment);
        Long id = enrollment.getId();

        enrollmentRepository.delete(enrollment);

        assertThat(enrollmentRepository.findById(id)).isEmpty();
    }
}
