package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private AuthController authController;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@test.com")
            .password("password")
            .build();
    }

    @Test
    public void testShowLoginForm() {
        String viewName = authController.showLoginForm();
        assertThat(viewName).isEqualTo("auth/login");
    }

    @Test
    public void testProcessLoginSuccess() {
        List<User> users = Arrays.asList(testUser);
        when(userService.findAll()).thenReturn(users);

        String viewName = authController.processLogin("test@test.com", "password", model);

        assertThat(viewName).isEqualTo("redirect:/courses");
        verify(userService).findAll();
    }

    @Test
    public void testProcessLoginUserNotFound() {
        when(userService.findAll()).thenReturn(Arrays.asList());

        String viewName = authController.processLogin("notfound@test.com", "password", model);

        assertThat(viewName).isEqualTo("auth/login");
        verify(model).addAttribute("error", "Kullanıcı bulunamadı!");
    }

    @Test
    public void testProcessLoginException() {
        when(userService.findAll()).thenThrow(new RuntimeException("DB Error"));

        String viewName = authController.processLogin("test@test.com", "password", model);

        assertThat(viewName).isEqualTo("auth/login");
        verify(model).addAttribute("error", "Giriş yaparken hata oluştu!");
    }

    @Test
    public void testShowRegisterForm() {
        String viewName = authController.showRegisterForm();
        assertThat(viewName).isEqualTo("auth/register");
    }

    @Test
    public void testProcessRegisterSuccess() {
        String viewName = authController.processRegister("John", "Doe", "john@test.com", "password", model);

        assertThat(viewName).isEqualTo("auth/login");
        verify(model).addAttribute("success", "Kayıt başarılı! Giriş yapabilirsiniz.");
    }

    @Test
    public void testLogout() {
        String viewName = authController.logout();
        assertThat(viewName).isEqualTo("redirect:/");
    }
}

