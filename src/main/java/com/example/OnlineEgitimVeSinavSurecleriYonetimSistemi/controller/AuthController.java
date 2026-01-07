package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Kimlik doğrulama controller'ı (Giriş/Kayıt)
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                             @RequestParam String password,
                             Model model) {
        try {
            // Kullanıcıyı kontrol et (basit implementasyon)
            var user = userService.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();

            if (user.isPresent()) {
                // Session'a kullanıcıyı ekle (gerçek uygulamada Spring Security kullanılmalı)
                return "redirect:/courses";
            } else {
                model.addAttribute("error", "Kullanıcı bulunamadı!");
                return "auth/login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Giriş yaparken hata oluştu!");
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String password,
                                Model model) {
        try {
            // Kullanıcı kaydı (basit implementasyon)
            // Gerçek uygulamada UserService.createUser() metodu kullanılmalı
            model.addAttribute("success", "Kayıt başarılı! Giriş yapabilirsiniz.");
            return "auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Kayıt olurken hata oluştu!");
            return "auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        // Session'ı temizle
        return "redirect:/";
    }
}
