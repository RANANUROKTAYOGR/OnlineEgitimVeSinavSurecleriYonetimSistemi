package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;

public interface NotificationService {
    Notification create(Notification notification);

    // Convenience method to support findAll()
    default java.util.List<Notification> findAll() {
        return java.util.Collections.emptyList(); // Default implementation
    }
}

