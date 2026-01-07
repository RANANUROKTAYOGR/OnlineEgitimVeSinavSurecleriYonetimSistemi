package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.NotificationRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Notification> getById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification update(Long id, Notification notification) {
        return notificationRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(notification, existing, "id");
            return notificationRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    @Override
    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}

