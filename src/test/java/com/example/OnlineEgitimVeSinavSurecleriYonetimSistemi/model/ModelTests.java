package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class ModelTests {

    @Test
    public void testCourseBuilder() {
        Course course = Course.builder()
            .id(1L)
            .title("Java Programming")
            .description("Learn Java")
            .price(99.99)
            .build();

        assertThat(course.getId()).isEqualTo(1L);
        assertThat(course.getTitle()).isEqualTo("Java Programming");
        assertThat(course.getDescription()).isEqualTo("Learn Java");
        assertThat(course.getPrice()).isEqualTo(99.99);
    }

    @Test
    public void testUserBuilder() {
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@test.com")
            .fullName("Test User")
            .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    public void testEnrollmentBuilder() {
        Enrollment enrollment = Enrollment.builder()
            .id(1L)
            .active(true)
            .completedLessons(5)
            .totalLessonsSnapshot(10)
            .build();

        assertThat(enrollment.getId()).isEqualTo(1L);
        assertThat(enrollment.getActive()).isTrue();
        assertThat(enrollment.getCompletedLessons()).isEqualTo(5);
        assertThat(enrollment.getTotalLessonsSnapshot()).isEqualTo(10);
    }

    @Test
    public void testQuizBuilder() {
        Quiz quiz = Quiz.builder()
            .id(1L)
            .title("Java Basics")
            .totalPoints(100)
            .build();

        assertThat(quiz.getId()).isEqualTo(1L);
        assertThat(quiz.getTitle()).isEqualTo("Java Basics");
        assertThat(quiz.getTotalPoints()).isEqualTo(100);
    }

    @Test
    public void testModuleBuilder() {
        Module module = Module.builder()
            .id(1L)
            .title("Introduction")
            .position(1)
            .build();

        assertThat(module.getId()).isEqualTo(1L);
        assertThat(module.getTitle()).isEqualTo("Introduction");
        assertThat(module.getPosition()).isEqualTo(1);
    }

    @Test
    public void testLessonBuilder() {
        Lesson lesson = Lesson.builder()
            .id(1L)
            .title("First Lesson")
            .content("Content here")
            .position(1)
            .build();

        assertThat(lesson.getId()).isEqualTo(1L);
        assertThat(lesson.getTitle()).isEqualTo("First Lesson");
        assertThat(lesson.getContent()).isEqualTo("Content here");
    }

    @Test
    public void testNotificationBuilder() {
        Notification notification = Notification.builder()
            .id(1L)
            .message("Test message")
            .readFlag(false)
            .build();

        assertThat(notification.getId()).isEqualTo(1L);
        assertThat(notification.getMessage()).isEqualTo("Test message");
        assertThat(notification.getReadFlag()).isFalse();
    }

    @Test
    public void testQuizQuestionBuilder() {
        QuizQuestion question = QuizQuestion.builder()
            .id(1L)
            .questionText("What is Java?")
            .correctChoice("A")
            .points(10)
            .build();

        assertThat(question.getId()).isEqualTo(1L);
        assertThat(question.getQuestionText()).isEqualTo("What is Java?");
        assertThat(question.getPoints()).isEqualTo(10);
    }

    @Test
    public void testSubmissionBuilder() {
        Submission submission = Submission.builder()
            .id(1L)
            .score(85.0)
            .build();

        assertThat(submission.getId()).isEqualTo(1L);
        assertThat(submission.getScore()).isEqualTo(85.0);
    }

    @Test
    public void testTransactionBuilder() {
        Transaction transaction = Transaction.builder()
            .id(1L)
            .amount(99.99)
            .status("COMPLETED")
            .build();

        assertThat(transaction.getId()).isEqualTo(1L);
        assertThat(transaction.getAmount()).isEqualTo(99.99);
        assertThat(transaction.getStatus()).isEqualTo("COMPLETED");
    }

    @Test
    public void testCertificateBuilder() {
        Certificate certificate = Certificate.builder()
            .id(1L)
            .certificateNumber("CERT123")
            .build();

        assertThat(certificate.getId()).isEqualTo(1L);
        assertThat(certificate.getCertificateNumber()).isEqualTo("CERT123");
    }

    @Test
    public void testUserRoleBuilder() {
        UserRole role = UserRole.builder()
            .id(1L)
            .name("ROLE_STUDENT")
            .build();

        assertThat(role.getId()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("ROLE_STUDENT");
    }
}

