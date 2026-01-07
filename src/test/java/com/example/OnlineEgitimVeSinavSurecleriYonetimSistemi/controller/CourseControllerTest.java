package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Lesson;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Quiz;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.CourseService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.ModuleService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.LessonService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.QuizService;
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
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private ModuleService moduleService;

    @Mock
    private LessonService lessonService;

    @Mock
    private QuizService quizService;

    @Mock
    private Model model;

    @InjectMocks
    private CourseController courseController;

    private Course course;
    private Module module;
    private Lesson lesson;
    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        course = Course.builder()
            .id(1L)
            .title("Test Course")
            .description("Test Description")
            .build();

        module = Module.builder()
            .id(1L)
            .course(course)
            .title("Test Module")
            .build();

        lesson = Lesson.builder()
            .id(1L)
            .title("Test Lesson")
            .build();

        quiz = Quiz.builder()
            .id(1L)
            .title("Test Quiz")
            .build();
    }

    @Test
    public void testListCourses() {
        List<Course> courses = Arrays.asList(course);
        when(courseService.findAll()).thenReturn(courses);

        String viewName = courseController.listCourses(model);

        assertThat(viewName).isEqualTo("courses/list");
        verify(model).addAttribute("courses", courses);
    }

    @Test
    public void testCourseDetail_success() {
        List<Module> modules = Arrays.asList(module);
        when(courseService.findById(1L)).thenReturn(course);
        when(moduleService.findAll()).thenReturn(modules);

        String viewName = courseController.courseDetail(1L, model);

        assertThat(viewName).isEqualTo("courses/detail");
        verify(model).addAttribute("course", course);
        verify(model).addAttribute(eq("modules"), anyList());
    }

    @Test
    public void testCourseDetail_notFound() {
        when(courseService.findById(1L)).thenThrow(new RuntimeException("Not found"));

        String viewName = courseController.courseDetail(1L, model);

        assertThat(viewName).isEqualTo("redirect:/courses");
    }

    @Test
    public void testLessonView_success() {
        when(courseService.findById(1L)).thenReturn(course);
        when(lessonService.findById(1L)).thenReturn(lesson);

        String viewName = courseController.lessonView(1L, 1L, model);

        assertThat(viewName).isEqualTo("courses/lesson");
        verify(model).addAttribute("course", course);
        verify(model).addAttribute("lesson", lesson);
    }

    @Test
    public void testLessonView_notFound() {
        when(courseService.findById(1L)).thenThrow(new RuntimeException("Not found"));

        String viewName = courseController.lessonView(1L, 1L, model);

        assertThat(viewName).isEqualTo("redirect:/courses/1");
    }

    @Test
    public void testQuizView_success() {
        when(courseService.findById(1L)).thenReturn(course);
        when(quizService.findById(1L)).thenReturn(quiz);

        String viewName = courseController.quizView(1L, 1L, model);

        assertThat(viewName).isEqualTo("courses/quiz");
        verify(model).addAttribute("course", course);
        verify(model).addAttribute("quiz", quiz);
    }

    @Test
    public void testQuizView_notFound() {
        when(courseService.findById(1L)).thenThrow(new RuntimeException("Not found"));

        String viewName = courseController.quizView(1L, 1L, model);

        assertThat(viewName).isEqualTo("redirect:/courses/1");
    }

    @Test
    public void testCheckout_success() {
        when(courseService.findById(1L)).thenReturn(course);

        String viewName = courseController.checkout(1L, model);

        assertThat(viewName).isEqualTo("checkout");
        verify(model).addAttribute("course", course);
    }

    @Test
    public void testCheckout_notFound() {
        when(courseService.findById(1L)).thenThrow(new RuntimeException("Not found"));

        String viewName = courseController.checkout(1L, model);

        assertThat(viewName).isEqualTo("redirect:/courses");
    }
}

