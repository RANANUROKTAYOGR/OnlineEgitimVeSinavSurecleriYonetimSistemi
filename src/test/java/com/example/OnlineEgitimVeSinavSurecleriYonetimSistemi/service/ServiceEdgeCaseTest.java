package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.*;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.*;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Comprehensive edge case tests for all service implementations
 */
@ExtendWith(MockitoExtension.class)
public class ServiceEdgeCaseTest {

    // User Service Tests
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionServiceImpl submissionService;

    @Test
    public void userService_findById_notFound_throwsException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(999L))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void userService_findAll_returnsAllUsers() {
        User user1 = User.builder().username("user1").build();
        User user2 = User.builder().username("user2").build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.findAll();

        assertThat(users).hasSize(2);
    }

    @Test
    public void courseService_findById_notFound_throwsException() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseService.findById(999L))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void courseService_findAll_returnsCourses() {
        Course course = Course.builder()
            .title("Test")
            .description("Desc")
            .build();

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        List<Course> courses = courseService.findAll();

        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getTitle()).isEqualTo("Test");
    }

    @Test
    public void enrollmentService_findAll_emptyList() {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList());

        List<Enrollment> result = enrollmentService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void enrollmentService_findById_exists() {
        Enrollment enrollment = Enrollment.builder()
            .active(true)
            .completedLessons(5)
            .build();

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        Enrollment found = enrollmentService.findById(1L);

        assertThat(found.getCompletedLessons()).isEqualTo(5);
    }

    @Test
    public void quizService_findById_found() {
        Quiz quiz = Quiz.builder()
            .id(1L)
            .title("Test Quiz")
            .build();

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Quiz found = quizService.findById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Test Quiz");
    }

    @Test
    public void quizService_findAll_returnsAllQuizzes() {
        Quiz q1 = Quiz.builder().title("Quiz1").build();
        Quiz q2 = Quiz.builder().title("Quiz2").build();

        when(quizRepository.findAll()).thenReturn(Arrays.asList(q1, q2));

        List<Quiz> quizzes = quizService.findAll();

        assertThat(quizzes).hasSize(2);
    }

    @Test
    public void submissionService_getById_exists() {
        Submission submission = Submission.builder()
            .score(95.0)
            .submittedAt(LocalDateTime.now())
            .build();

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));

        Optional<Submission> found = submissionService.getById(1L);

        assertThat(found).isPresent();
        assertThat(found.get().getScore()).isEqualTo(95.0);
        assertThat(found.get().getSubmittedAt()).isNotNull();
    }

    @Test
    public void submissionService_getById_notFound() {
        when(submissionRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Submission> found = submissionService.getById(999L);

        assertThat(found).isEmpty();
    }
}

