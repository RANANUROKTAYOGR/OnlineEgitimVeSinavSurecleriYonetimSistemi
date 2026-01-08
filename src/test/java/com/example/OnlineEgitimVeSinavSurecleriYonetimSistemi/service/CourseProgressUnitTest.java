package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CourseProgressUnitTest {

    @Test
    void calculateProgress_whenSnapshotZero_populatesSnapshotAndCalculates() {
        CourseRepository courseRepo = Mockito.mock(CourseRepository.class);
        EnrollmentRepository enrollmentRepo = Mockito.mock(EnrollmentRepository.class);
        CourseServiceImpl service = new CourseServiceImpl(courseRepo, enrollmentRepo);

        Course course = new Course();
        course.setId(100L);
        Module m1 = new Module();
        Lesson l1 = new Lesson(); l1.setId(1L);
        Lesson l2 = new Lesson(); l2.setId(2L);
        m1.setLessons(Set.of(l1, l2));
        course.setModules(Set.of(m1));

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(null); // will set user id via a small fake
        enrollment.setTotalLessonsSnapshot(0);
        enrollment.setCompletedLessons(1);
        // put a stub findAll returning this enrollment but with user id matching
        var user = new com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User();
        user.setId(500L);
        enrollment.setUser(user);

        Mockito.when(courseRepo.findById(100L)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentRepo.findAll()).thenReturn(List.of(enrollment));
        Mockito.when(enrollmentRepo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        double pct = service.calculateProgressPercentage(100L, 500L);
        assertThat(pct).isEqualTo((1.0 * 100.0) / 2.0);
        // ensure snapshot updated
        assertThat(enrollment.getTotalLessonsSnapshot()).isEqualTo(2);
    }

    @Test
    void calculateProgress_whenNoEnrollment_returnsZero() {
        CourseRepository courseRepo = Mockito.mock(CourseRepository.class);
        EnrollmentRepository enrollmentRepo = Mockito.mock(EnrollmentRepository.class);
        CourseServiceImpl service = new CourseServiceImpl(courseRepo, enrollmentRepo);

        Course course = new Course();
        course.setId(200L);
        Mockito.when(courseRepo.findById(200L)).thenReturn(Optional.of(course));
        Mockito.when(enrollmentRepo.findAll()).thenReturn(List.of());

        double pct = service.calculateProgressPercentage(200L, 999L);
        assertThat(pct).isEqualTo(0.0);
    }
}
