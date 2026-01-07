package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;

public interface NotificationService {
    Notification create(Notification notification);
    
    // Method for listing notifications - to be implemented by the service
    java.util.List<Notification> findAll();
}

