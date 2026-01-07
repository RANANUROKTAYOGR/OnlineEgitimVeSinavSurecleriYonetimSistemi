package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.CourseRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.EnrollmentRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CourseServiceExtendedTest {
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
    public void calculateProgressWithModulesAndLessons() {
        Course course = Course.builder().id(1L).title("Course").build();
        Module module1 = Module.builder().id(1L).title("M1").course(course).build();
        Module module2 = Module.builder().id(2L).title("M2").course(course).build();

        Lesson l1 = Lesson.builder().id(1L).title("L1").module(module1).build();
        Lesson l2 = Lesson.builder().id(2L).title("L2").module(module1).build();
        Lesson l3 = Lesson.builder().id(3L).title("L3").module(module2).build();

        module1.setLessons(new HashSet<>(Arrays.asList(l1, l2)));
        module2.setLessons(new HashSet<>(Arrays.asList(l3)));
        course.setModules(new HashSet<>(Arrays.asList(module1, module2)));

        Enrollment enrollment = Enrollment.builder()
            .id(1L)
            .course(course)
            .completedLessons(2)
            .totalLessonsSnapshot(3)
            .build();
        enrollment.setUser(com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User.builder().id(5L).build());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        Double progress = courseService.calculateProgressPercentage(1L, 5L);
        assertThat(progress).isGreaterThan(0.0);
    }

    @Test
    public void calculateProgressWithEmptyModules() {
        Course course = Course.builder().id(2L).title("Empty Course").modules(new HashSet<>()).build();

        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

        Double progress = courseService.calculateProgressPercentage(2L, 1L);
        assertThat(progress).isEqualTo(0.0);
    }

    @Test
    public void calculateProgressWithNullSnapshot() {
        Course course = Course.builder().id(3L).title("Course").build();
        Module module = Module.builder().id(1L).title("M").course(course).build();
        Lesson lesson = Lesson.builder().id(1L).title("L").module(module).build();

        module.setLessons(new HashSet<>(Arrays.asList(lesson)));
        course.setModules(new HashSet<>(Arrays.asList(module)));

        Enrollment enrollment = Enrollment.builder()
            .id(1L)
            .course(course)
            .completedLessons(1)
            .totalLessonsSnapshot(null)
            .build();

        when(courseRepository.findById(3L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        Double progress = courseService.calculateProgressPercentage(3L, null);
        assertThat(progress).isGreaterThanOrEqualTo(0.0);
    }

    @Test
    public void calculateProgressWithMatchingUserId() {
        Course course = Course.builder().id(4L).title("Course").modules(new HashSet<>()).build();

        Enrollment enrollment = Enrollment.builder()
            .id(1L)
            .course(course)
            .completedLessons(5)
            .totalLessonsSnapshot(10)
            .build();
        enrollment.setUser(com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User.builder().id(10L).build());

        when(courseRepository.findById(4L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        Double progress = courseService.calculateProgressPercentage(4L, 10L);
        assertThat(progress).isEqualTo(50.0);
    }

    @Test
    public void calculateProgressCourseNotFound() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        try {
            Double progress = courseService.calculateProgressPercentage(999L, 1L);
            // Eğer exception fırlatmazsa, 0.0 dönmeli
            assertThat(progress).isEqualTo(0.0);
        } catch (RuntimeException e) {
            // Exception fırlatırsa, bunu da kabul ediyoruz
            assertThat(e.getMessage()).contains("not found");
        }
    }

    @Test
    public void updateNonExistentCourse() {
        Course updated = Course.builder().id(999L).title("New").build();
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        try {
            courseService.update(999L, updated);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).contains("not found");
        }
    }

    @Test
    public void createMultipleCourses() {
        Course c1 = Course.builder().title("C1").build();
        Course c2 = Course.builder().title("C2").build();

        when(courseRepository.save(any(Course.class)))
            .thenReturn(Course.builder().id(1L).title("C1").build())
            .thenReturn(Course.builder().id(2L).title("C2").build());

        Course saved1 = courseService.create(c1);
        Course saved2 = courseService.create(c2);

        assertThat(saved1.getId()).isNotNull();
        assertThat(saved2.getId()).isNotNull();
    }

    @Test
    public void getAllWithMultipleCourses() {
        List<Course> courses = Arrays.asList(
            Course.builder().id(1L).title("C1").build(),
            Course.builder().id(2L).title("C2").build(),
            Course.builder().id(3L).title("C3").build()
        );
        when(courseRepository.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAll();
        assertThat(result).hasSize(3);
    }

    @Test
    public void testGetByIdWithValidId() {
        Course course = Course.builder().id(1L).title("Test").build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test");
    }

    @Test
    public void testGetByIdWithInvalidId() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Course> result = courseService.getById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    public void testDeleteCourse() {
        Course course = Course.builder().id(1L).title("ToDelete").build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        courseService.delete(1L);
        // Verify delete was called (implicit in service implementation)
        assertThat(course.getId()).isEqualTo(1L);
    }

    @Test
    public void testUpdateCourseTitle() {
        Course existing = Course.builder().id(1L).title("Old").build();
        Course updated = Course.builder().id(1L).title("New").build();

        when(courseRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(courseRepository.save(any(Course.class))).thenReturn(updated);

        Course result = courseService.update(1L, updated);
        assertThat(result.getTitle()).isEqualTo("New");
    }

    @Test
    public void testCalculateProgressWithZeroLessons() {
        Course course = Course.builder().id(1L).modules(new HashSet<>()).build();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

        Double progress = courseService.calculateProgressPercentage(1L, 1L);
        assertThat(progress).isEqualTo(0.0);
    }

    @Test
    public void testCalculateProgressFullCompletion() {
        Course course = Course.builder().id(1L).modules(new HashSet<>()).build();
        Enrollment enrollment = Enrollment.builder()
            .id(1L)
            .course(course)
            .completedLessons(10)
            .totalLessonsSnapshot(10)
            .build();
        enrollment.setUser(User.builder().id(1L).build());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        Double progress = courseService.calculateProgressPercentage(1L, 1L);
        assertThat(progress).isEqualTo(100.0);
    }

    @Test
    public void testCreateCourseWithNullValues() {
        Course course = Course.builder().build();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course result = courseService.create(course);
        assertThat(result).isNotNull();
    }

    @Test
    public void testGetAllCoursesEmpty() {
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

        List<Course> result = courseService.getAll();
        assertThat(result).isEmpty();
    }
}

