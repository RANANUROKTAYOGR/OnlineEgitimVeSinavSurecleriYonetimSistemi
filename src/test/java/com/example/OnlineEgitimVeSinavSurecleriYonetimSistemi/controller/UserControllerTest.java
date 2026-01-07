package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.EnrollmentService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    private User user;
    private Enrollment enrollment;
    private Notification notification;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .password("password")
            .build();

        enrollment = Enrollment.builder()
            .id(1L)
            .user(user)
            .active(true)
            .build();

        notification = Notification.builder()
            .id(1L)
            .message("Test notification")
            .readFlag(false)
            .build();
    }

    @Test
    public void testProfile_withUser() {
        List<User> users = Arrays.asList(user);
        List<Enrollment> enrollments = Arrays.asList(enrollment);

        when(userService.findAll()).thenReturn(users);
        when(enrollmentService.findAll()).thenReturn(enrollments);

        String viewName = userController.profile(model);

        assertThat(viewName).isEqualTo("user/profile");
        verify(model).addAttribute("user", user);
        verify(model).addAttribute(eq("enrollments"), anyList());
    }

    @Test
    public void testProfile_withoutUser() {
        when(userService.findAll()).thenReturn(Arrays.asList());

        String viewName = userController.profile(model);

        assertThat(viewName).isEqualTo("user/profile");
        verify(model, never()).addAttribute(eq("user"), any());
    }

    @Test
    public void testNotifications() {
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationService.findAll()).thenReturn(notifications);

        String viewName = userController.notifications(model);

        assertThat(viewName).isEqualTo("user/notifications");
        verify(model).addAttribute("notifications", notifications);
    }

    @Test
    public void testDashboard_withUser() {
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        when(enrollmentService.findAll()).thenReturn(enrollments);

        String viewName = userController.dashboard(model);

        assertThat(viewName).isEqualTo("user/profile");
        verify(model).addAttribute(eq("totalCourses"), any());
        verify(model).addAttribute(eq("completedCourses"), any());
        verify(model).addAttribute(eq("inProgressCourses"), any());
    }

    @Test
    public void testDashboard_withoutUser() {
        when(enrollmentService.findAll()).thenReturn(Arrays.asList());

        String viewName = userController.dashboard(model);

        assertThat(viewName).isEqualTo("user/profile");
        verify(model).addAttribute(eq("totalCourses"), any());
        verify(model).addAttribute(eq("completedCourses"), any());
        verify(model).addAttribute(eq("inProgressCourses"), any());
    }
}

