package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.NotificationRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }
}

