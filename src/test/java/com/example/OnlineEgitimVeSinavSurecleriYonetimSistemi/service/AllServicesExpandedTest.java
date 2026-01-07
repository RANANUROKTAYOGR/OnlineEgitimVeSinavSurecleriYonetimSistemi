package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.*;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.*;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AllServicesExpandedTest {

    @Mock private UserRepository userRepository;
    @Mock private ModuleRepository moduleRepository;
    @Mock private LessonRepository lessonRepository;
    @Mock private QuizRepository quizRepository;
    @Mock private QuizQuestionRepository quizQuestionRepository;
    @Mock private SubmissionRepository submissionRepository;
    @Mock private NotificationRepository notificationRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private UserRoleRepository userRoleRepository;
    @Mock private CertificateRepository certificateRepository;

    private UserServiceImpl userService;
    private ModuleServiceImpl moduleService;
    private LessonServiceImpl lessonService;
    private QuizServiceImpl quizService;
    private QuizQuestionServiceImpl quizQuestionService;
    private SubmissionServiceImpl submissionService;
    private NotificationServiceImpl notificationService;
    private TransactionServiceImpl transactionService;
    private UserRoleServiceImpl userRoleService;
    private CertificateServiceImpl certificateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
        moduleService = new ModuleServiceImpl(moduleRepository);
        lessonService = new LessonServiceImpl(lessonRepository);
        quizService = new QuizServiceImpl(quizRepository);
        quizQuestionService = new QuizQuestionServiceImpl(quizQuestionRepository);
        submissionService = new SubmissionServiceImpl(submissionRepository);
        notificationService = new NotificationServiceImpl(notificationRepository);
        transactionService = new TransactionServiceImpl(transactionRepository);
        userRoleService = new UserRoleServiceImpl(userRoleRepository);
        certificateService = new CertificateServiceImpl(certificateRepository);
    }

    // User Service Tests
    @Test
    public void testUserServiceCreate() {
        User user = User.builder().username("test").build();
        when(userRepository.save(any(User.class))).thenReturn(User.builder().id(1L).username("test").build());

        User created = userService.create(user);
        assertThat(created.getId()).isEqualTo(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUserServiceGetAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(
            User.builder().id(1L).build(),
            User.builder().id(2L).build()
        ));

        List<User> users = userService.getAll();
        assertThat(users).hasSize(2);
    }

    @Test
    public void testUserServiceGetById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().id(1L).build()));

        Optional<User> user = userService.getById(1L);
        assertThat(user).isPresent();
    }

    @Test
    public void testUserServiceUpdate() {
        User existing = User.builder().id(1L).username("old").build();
        User updated = User.builder().id(1L).username("new").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(updated);

        User result = userService.update(1L, updated);
        assertThat(result.getUsername()).isEqualTo("new");
    }

    @Test
    public void testUserServiceDelete() {
        doNothing().when(userRepository).deleteById(anyLong());
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    // Module Service Tests
    @Test
    public void testModuleServiceCreate() {
        com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module module =
            com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module.builder().title("M1").build();
        when(moduleRepository.save(any())).thenReturn(
            com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module.builder().id(1L).title("M1").build()
        );

        com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module created = moduleService.create(module);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testModuleServiceGetAll() {
        when(moduleRepository.findAll()).thenReturn(Arrays.asList(
            com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module.builder().id(1L).build(),
            com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module.builder().id(2L).build(),
            com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module.builder().id(3L).build()
        ));

        List<com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module> modules = moduleService.getAll();
        assertThat(modules).hasSize(3);
    }

    // Lesson Service Tests
    @Test
    public void testLessonServiceCreate() {
        Lesson lesson = Lesson.builder().title("L1").build();
        when(lessonRepository.save(any(Lesson.class))).thenReturn(Lesson.builder().id(1L).title("L1").build());

        Lesson created = lessonService.create(lesson);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testLessonServiceUpdate() {
        Lesson existing = Lesson.builder().id(1L).title("old").build();
        Lesson updated = Lesson.builder().id(1L).title("new").build();

        when(lessonRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(updated);

        Lesson result = lessonService.update(1L, updated);
        assertThat(result.getTitle()).isEqualTo("new");
    }

    @Test
    public void testLessonServiceDelete() {
        doNothing().when(lessonRepository).deleteById(anyLong());
        lessonService.delete(1L);
        verify(lessonRepository, times(1)).deleteById(1L);
    }

    // Quiz Service Tests
    @Test
    public void testQuizServiceCreate() {
        Quiz quiz = Quiz.builder().title("Q1").build();
        when(quizRepository.save(any(Quiz.class))).thenReturn(Quiz.builder().id(1L).title("Q1").build());

        Quiz created = quizService.create(quiz);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testQuizServiceGetById() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(Quiz.builder().id(1L).build()));

        Optional<Quiz> quiz = quizService.getById(1L);
        assertThat(quiz).isPresent();
    }

    @Test
    public void testQuizServiceDelete() {
        doNothing().when(quizRepository).deleteById(anyLong());
        quizService.delete(1L);
        verify(quizRepository, times(1)).deleteById(1L);
    }

    // QuizQuestion Service Tests
    @Test
    public void testQuizQuestionServiceCreate() {
        QuizQuestion question = QuizQuestion.builder().questionText("Q?").build();
        when(quizQuestionRepository.save(any(QuizQuestion.class)))
            .thenReturn(QuizQuestion.builder().id(1L).questionText("Q?").build());

        QuizQuestion created = quizQuestionService.create(question);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testQuizQuestionServiceUpdate() {
        QuizQuestion existing = QuizQuestion.builder().id(1L).questionText("old").build();
        QuizQuestion updated = QuizQuestion.builder().id(1L).questionText("new").build();

        when(quizQuestionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(quizQuestionRepository.save(any(QuizQuestion.class))).thenReturn(updated);

        QuizQuestion result = quizQuestionService.update(1L, updated);
        assertThat(result.getQuestionText()).isEqualTo("new");
    }

    @Test
    public void testQuizQuestionServiceDelete() {
        doNothing().when(quizQuestionRepository).deleteById(anyLong());
        quizQuestionService.delete(1L);
        verify(quizQuestionRepository, times(1)).deleteById(1L);
    }

    // Submission Service Tests
    @Test
    public void testSubmissionServiceCreate() {
        Submission submission = Submission.builder().score(80.0).build();
        when(submissionRepository.save(any(Submission.class)))
            .thenReturn(Submission.builder().id(1L).score(80.0).build());

        Submission created = submissionService.create(submission);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testSubmissionServiceGetById() {
        when(submissionRepository.findById(1L))
            .thenReturn(Optional.of(Submission.builder().id(1L).build()));

        Optional<Submission> submission = submissionService.getById(1L);
        assertThat(submission).isPresent();
    }

    @Test
    public void testSubmissionServiceUpdate() {
        Submission existing = Submission.builder().id(1L).score(70.0).build();
        Submission updated = Submission.builder().id(1L).score(90.0).build();

        when(submissionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(submissionRepository.save(any(Submission.class))).thenReturn(updated);

        Submission result = submissionService.update(1L, updated);
        assertThat(result.getScore()).isEqualTo(90.0);
    }

    @Test
    public void testSubmissionServiceDelete() {
        doNothing().when(submissionRepository).deleteById(anyLong());
        submissionService.delete(1L);
        verify(submissionRepository, times(1)).deleteById(1L);
    }

    // Notification Service Tests
    @Test
    public void testNotificationServiceCreate() {
        Notification notification = Notification.builder().message("msg").build();
        when(notificationRepository.save(any(Notification.class)))
            .thenReturn(Notification.builder().id(1L).message("msg").build());

        Notification created = notificationService.create(notification);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testNotificationServiceGetAll() {
        when(notificationRepository.findAll()).thenReturn(Arrays.asList(
            Notification.builder().id(1L).build(),
            Notification.builder().id(2L).build()
        ));

        List<Notification> notifications = notificationService.getAll();
        assertThat(notifications).hasSize(2);
    }

    @Test
    public void testNotificationServiceDelete() {
        doNothing().when(notificationRepository).deleteById(anyLong());
        notificationService.delete(1L);
        verify(notificationRepository, times(1)).deleteById(1L);
    }

    // Transaction Service Tests
    @Test
    public void testTransactionServiceCreate() {
        Transaction transaction = Transaction.builder().amount(100.0).build();
        when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(Transaction.builder().id(1L).amount(100.0).build());

        Transaction created = transactionService.create(transaction);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testTransactionServiceGetById() {
        when(transactionRepository.findById(1L))
            .thenReturn(Optional.of(Transaction.builder().id(1L).build()));

        Optional<Transaction> transaction = transactionService.getById(1L);
        assertThat(transaction).isPresent();
    }

    @Test
    public void testTransactionServiceUpdate() {
        Transaction existing = Transaction.builder().id(1L).status("PENDING").build();
        Transaction updated = Transaction.builder().id(1L).status("COMPLETED").build();

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(updated);

        Transaction result = transactionService.update(1L, updated);
        assertThat(result.getStatus()).isEqualTo("COMPLETED");
    }

    // UserRole Service Tests
    @Test
    public void testUserRoleServiceCreate() {
        UserRole userRole = UserRole.builder().build();
        when(userRoleRepository.save(any(UserRole.class)))
            .thenReturn(UserRole.builder().id(1L).build());

        UserRole created = userRoleService.create(userRole);
        assertThat(created.getId()).isEqualTo(1L);
    }


    // Certificate Service Tests
    @Test
    public void testCertificateServiceCreate() {
        Certificate certificate = Certificate.builder().build();
        when(certificateRepository.save(any(Certificate.class)))
            .thenReturn(Certificate.builder().id(1L).build());

        Certificate created = certificateService.create(certificate);
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    public void testCertificateServiceGetById() {
        when(certificateRepository.findById(1L))
            .thenReturn(Optional.of(Certificate.builder().id(1L).build()));

        Optional<Certificate> certificate = certificateService.getById(1L);
        assertThat(certificate).isPresent();
    }

    @Test
    public void testCertificateServiceUpdate() {
        Certificate existing = Certificate.builder().id(1L).build();
        Certificate updated = Certificate.builder().id(1L).build();

        when(certificateRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(certificateRepository.save(any(Certificate.class))).thenReturn(updated);

        Certificate result = certificateService.update(1L, updated);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    public void testCertificateServiceDelete() {
        doNothing().when(certificateRepository).deleteById(anyLong());
        certificateService.delete(1L);
        verify(certificateRepository, times(1)).deleteById(1L);
    }
}

