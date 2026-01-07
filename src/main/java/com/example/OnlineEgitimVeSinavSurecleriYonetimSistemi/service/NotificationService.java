package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification create(Notification notification);
    Optional<Notification> getById(Long id);
    Notification findById(Long id);
    List<Notification> getAll();
    List<Notification> findAll();
    Notification update(Long id, Notification notification);
    void delete(Long id);
}

