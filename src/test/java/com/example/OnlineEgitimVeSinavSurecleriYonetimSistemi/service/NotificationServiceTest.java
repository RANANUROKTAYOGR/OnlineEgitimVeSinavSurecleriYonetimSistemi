package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class NotificationServiceTest {
    @Mock
    private NotificationService notificationService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createNotification_saves() {
        Notification n = Notification.builder().title("t").message("m").createdAt(LocalDateTime.now()).build();
        when(notificationService.create(any(Notification.class))).thenReturn(Notification.builder().id(1L).title("t").build());
        var saved = notificationService.create(n);
        assertThat(saved.getId()).isEqualTo(1L);
    }
}
