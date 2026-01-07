package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.CourseService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    private List<Course> courses;
    private List<User> users;

    @BeforeEach
    public void setUp() {
        courses = Arrays.asList(
            Course.builder().id(1L).title("Course 1").build(),
            Course.builder().id(2L).title("Course 2").build(),
            Course.builder().id(3L).title("Course 3").build()
        );

        users = Arrays.asList(
            User.builder().id(1L).username("user1").build(),
            User.builder().id(2L).username("user2").build()
        );
    }

    @Test
    public void testHome() {
        when(courseService.findAll()).thenReturn(courses);
        when(userService.findAll()).thenReturn(users);

        String viewName = homeController.home(model);

        assertThat(viewName).isEqualTo("index");
        verify(model).addAttribute(eq("popularCourses"), anyList());
        verify(model).addAttribute("totalCourses", 3);
        verify(model).addAttribute("totalUsers", 2);
    }

    @Test
    public void testAbout() {
        String viewName = homeController.about();
        assertThat(viewName).isEqualTo("about");
    }

    @Test
    public void testContact() {
        String viewName = homeController.contact();
        assertThat(viewName).isEqualTo("contact");
    }
}

