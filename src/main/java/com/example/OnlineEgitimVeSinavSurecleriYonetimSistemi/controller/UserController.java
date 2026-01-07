package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.NotificationService;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Kullanıcı profil ve dashboard controller'ı
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final NotificationService notificationService;
    private final EnrollmentService enrollmentService;

    @GetMapping("/profile")
    public String profile(Model model) {
        // Demo için ilk kullanıcıyı al
        var users = userService.findAll();
        if (!users.isEmpty()) {
            var user = users.get(0);
            var enrollments = enrollmentService.findAll().stream()
                .filter(e -> e.getUser().getId().equals(user.getId()))
                .toList();

            model.addAttribute("user", user);
            model.addAttribute("enrollments", enrollments);
        }
        return "user/profile";
    }

    @GetMapping("/notifications")
    public String notifications(Model model) {
        // Demo için tüm bildirimleri al
        var notifications = notificationService.findAll();
        model.addAttribute("notifications", notifications);
        return "user/notifications";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Dashboard istatistikleri
        model.addAttribute("totalCourses", enrollmentService.findAll().size());
        model.addAttribute("completedCourses", 0);
        model.addAttribute("inProgressCourses", enrollmentService.findAll().size());
        return "user/profile"; // Profile sayfasını dashboard olarak kullan
    }
}
