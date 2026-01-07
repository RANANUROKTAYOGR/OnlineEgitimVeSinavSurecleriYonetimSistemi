package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.CourseService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Ana sayfa ve genel sayfa controller'ı
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CourseService courseService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        // En popüler kursları getir (ilk 6 tanesi)
        var popularCourses = courseService.findAll().stream().limit(6).toList();
        model.addAttribute("popularCourses", popularCourses);
        model.addAttribute("totalCourses", courseService.findAll().size());
        model.addAttribute("totalUsers", userService.findAll().size());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
