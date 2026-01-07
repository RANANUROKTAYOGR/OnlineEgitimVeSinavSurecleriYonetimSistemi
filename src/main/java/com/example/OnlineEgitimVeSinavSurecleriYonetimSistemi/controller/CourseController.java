package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.CourseService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.ModuleService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.LessonService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Kurs yönetimi controller'ı
 */
@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final ModuleService moduleService;
    private final LessonService lessonService;
    private final QuizService quizService;

    @GetMapping
    public String listCourses(Model model) {
        var courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "courses/list";
    }

    @GetMapping("/{courseId}")
    public String courseDetail(@PathVariable Long courseId, Model model) {
        try {
            var course = courseService.findById(courseId);
            var modules = moduleService.findAll().stream()
                .filter(m -> m.getCourse().getId().equals(courseId))
                .toList();

            model.addAttribute("course", course);
            model.addAttribute("modules", modules);
            return "courses/detail";
        } catch (Exception e) {
            return "redirect:/courses";
        }
    }

    @GetMapping("/{courseId}/lessons/{lessonId}")
    public String lessonView(@PathVariable Long courseId,
                           @PathVariable Long lessonId,
                           Model model) {
        try {
            var course = courseService.findById(courseId);
            var lesson = lessonService.findById(lessonId);

            model.addAttribute("course", course);
            model.addAttribute("lesson", lesson);
            return "courses/lesson";
        } catch (Exception e) {
            return "redirect:/courses/" + courseId;
        }
    }

    @GetMapping("/{courseId}/quiz/{quizId}")
    public String quizView(@PathVariable Long courseId,
                          @PathVariable Long quizId,
                          Model model) {
        try {
            var course = courseService.findById(courseId);
            var quiz = quizService.findById(quizId);

            model.addAttribute("course", course);
            model.addAttribute("quiz", quiz);
            return "courses/quiz";
        } catch (Exception e) {
            return "redirect:/courses/" + courseId;
        }
    }

    @GetMapping("/checkout/{courseId}")
    public String checkout(@PathVariable Long courseId, Model model) {
        try {
            var course = courseService.findById(courseId);
            model.addAttribute("course", course);
            return "checkout";
        } catch (Exception e) {
            return "redirect:/courses";
        }
    }
}
