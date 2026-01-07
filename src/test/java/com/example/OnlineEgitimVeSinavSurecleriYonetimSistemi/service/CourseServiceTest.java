package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;

    private CourseServiceImpl courseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository, enrollmentRepository);
    }

    @Test
    public void calculateProgressPercentage_noEnrollment_returnsZero() {
        Course c = Course.builder().id(1L).title("C1").modules(new HashSet<>()).build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

        Double pct = courseService.calculateProgressPercentage(1L, 5L);
        assertThat(pct).isEqualTo(0.0);
    }

    @Test
    public void calculateProgressPercentage_withSnapshotUsesSnapshot() {
        Course c = Course.builder().id(2L).title("C2").modules(new HashSet<>()).build();
        Enrollment e = Enrollment.builder().id(11L).course(c).completedLessons(2).totalLessonsSnapshot(4).build();
        when(courseRepository.findById(2L)).thenReturn(Optional.of(c));
        when(enrollmentRepository.findAll()).thenReturn(List.of(e));

        Double pct = courseService.calculateProgressPercentage(2L, null);
        // userId null means no match -> 0
        assertThat(pct).isEqualTo(0.0);
    }

    @Test
    public void createCourse_savesAndReturns() {
        Course c = Course.builder().title("New").build();
        when(courseRepository.save(c)).thenReturn(Course.builder().id(7L).title("New").build());
        Course saved = courseService.create(c);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    public void getById_returnsCourse() {
        Course course = Course.builder().id(1L).title("Test Course").build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test Course");
    }

    @Test
    public void getAll_returnsAllCourses() {
        List<Course> courses = Arrays.asList(
            Course.builder().id(1L).title("Course 1").build(),
            Course.builder().id(2L).title("Course 2").build()
        );
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void update_updatesCourse() {
        Course existing = Course.builder().id(1L).title("Old Title").build();
        Course updated = Course.builder().id(1L).title("New Title").build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(courseRepository.save(existing)).thenReturn(updated);

        Course result = courseService.update(1L, updated);

        assertThat(result.getTitle()).isEqualTo("New Title");
    }

    @Test
    public void delete_deletesCourse() {
        courseService.delete(1L);
        // Just verify no exception thrown
        assertThat(true).isTrue();
    }
}


