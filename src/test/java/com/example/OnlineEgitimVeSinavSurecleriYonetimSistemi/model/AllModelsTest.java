package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AllModelsTest {

    @Test
    public void testUserBuilder() {
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@test.com")
            .password("pass")
            .createdAt(LocalDateTime.now())
            .build();

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@test.com");
        assertThat(user.getPassword()).isEqualTo("pass");
        assertThat(user.getCreatedAt()).isNotNull();
    }

    @Test
    public void testCourseBuilder() {
        Course course = Course.builder()
            .id(1L)
            .title("Java 101")
            .description("Intro to Java")
            .price(99.99)
            .build();

        assertThat(course.getId()).isEqualTo(1L);
        assertThat(course.getTitle()).isEqualTo("Java 101");
        assertThat(course.getDescription()).isEqualTo("Intro to Java");
        assertThat(course.getPrice()).isEqualTo(99.99);
    }

    @Test
    public void testModuleBuilder() {
        Module module = Module.builder()
            .id(1L)
            .title("Module 1")
            .build();

        assertThat(module.getId()).isEqualTo(1L);
        assertThat(module.getTitle()).isEqualTo("Module 1");
    }

    @Test
    public void testLessonBuilder() {
        Lesson lesson = Lesson.builder()
            .id(1L)
            .title("Lesson 1")
            .content("Content")
            .build();

        assertThat(lesson.getId()).isEqualTo(1L);
        assertThat(lesson.getTitle()).isEqualTo("Lesson 1");
        assertThat(lesson.getContent()).isEqualTo("Content");
    }

    @Test
    public void testQuizBuilder() {
        Quiz quiz = Quiz.builder()
            .id(1L)
            .title("Quiz 1")
            .build();

        assertThat(quiz.getId()).isEqualTo(1L);
        assertThat(quiz.getTitle()).isEqualTo("Quiz 1");
    }

    @Test
    public void testQuizQuestionBuilder() {
        QuizQuestion question = QuizQuestion.builder()
            .id(1L)
            .questionText("What is Java?")
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
            .submittedAt(LocalDateTime.now())
            .build();

        assertThat(submission.getId()).isEqualTo(1L);
        assertThat(submission.getScore()).isEqualTo(85.0);
    }

    @Test
    public void testEnrollmentBuilder() {
        Enrollment enrollment = Enrollment.builder()
            .id(1L)
            .enrolledAt(LocalDateTime.now())
            .build();

        assertThat(enrollment.getId()).isEqualTo(1L);
        assertThat(enrollment.getEnrolledAt()).isNotNull();
    }

    @Test
    public void testCertificateBuilder() {
        Certificate certificate = Certificate.builder()
            .id(1L)
            .issuedAt(LocalDateTime.now())
            .build();

        assertThat(certificate.getId()).isEqualTo(1L);
    }

    @Test
    public void testNotificationBuilder() {
        Notification notification = Notification.builder()
            .id(1L)
            .message("Test notification")
            .createdAt(LocalDateTime.now())
            .build();

        assertThat(notification.getId()).isEqualTo(1L);
        assertThat(notification.getMessage()).isEqualTo("Test notification");
    }

    @Test
    public void testTransactionBuilder() {
        Transaction transaction = Transaction.builder()
            .id(1L)
            .amount(100.0)
            .status("COMPLETED")
            .build();

        assertThat(transaction.getId()).isEqualTo(1L);
        assertThat(transaction.getAmount()).isEqualTo(100.0);
        assertThat(transaction.getStatus()).isEqualTo("COMPLETED");
    }

    @Test
    public void testUserRoleBuilder() {
        UserRole userRole = UserRole.builder()
            .id(1L)
            .build();

        assertThat(userRole.getId()).isEqualTo(1L);
    }

    @Test
    public void testUserSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("pass");

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("test");
    }

    @Test
    public void testCourseSetters() {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Java");
        course.setDescription("Desc");
        course.setPrice(99.99);

        assertThat(course.getId()).isEqualTo(1L);
        assertThat(course.getTitle()).isEqualTo("Java");
    }

    @Test
    public void testModuleSetters() {
        Module module = new Module();
        module.setId(1L);
        module.setTitle("Module");

        assertThat(module.getId()).isEqualTo(1L);
    }

    @Test
    public void testLessonSetters() {
        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setTitle("Lesson");
        lesson.setContent("Content");

        assertThat(lesson.getId()).isEqualTo(1L);
    }

    @Test
    public void testQuizSetters() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Quiz");

        assertThat(quiz.getId()).isEqualTo(1L);
    }

    @Test
    public void testQuizQuestionSetters() {
        QuizQuestion q = new QuizQuestion();
        q.setId(1L);
        q.setQuestionText("Q?");

        assertThat(q.getId()).isEqualTo(1L);
    }

    @Test
    public void testSubmissionSetters() {
        Submission s = new Submission();
        s.setId(1L);
        s.setScore(90.0);

        assertThat(s.getId()).isEqualTo(1L);
    }

    @Test
    public void testEnrollmentSetters() {
        Enrollment e = new Enrollment();
        e.setId(1L);
        e.setEnrolledAt(LocalDateTime.now());

        assertThat(e.getId()).isEqualTo(1L);
    }

    @Test
    public void testCertificateSetters() {
        Certificate c = new Certificate();
        c.setId(1L);

        assertThat(c.getId()).isEqualTo(1L);
    }

    @Test
    public void testNotificationSetters() {
        Notification n = new Notification();
        n.setId(1L);
        n.setMessage("msg");

        assertThat(n.getId()).isEqualTo(1L);
    }

    @Test
    public void testTransactionSetters() {
        Transaction t = new Transaction();
        t.setId(1L);
        t.setAmount(100.0);
        t.setStatus("COMPLETED");

        assertThat(t.getId()).isEqualTo(1L);
    }

    @Test
    public void testUserRoleSetters() {
        UserRole r = new UserRole();
        r.setId(1L);

        assertThat(r.getId()).isEqualTo(1L);
    }
}

