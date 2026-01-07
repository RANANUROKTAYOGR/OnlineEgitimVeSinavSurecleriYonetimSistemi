package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Notification;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.NotificationRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificationServiceExtendedTest {
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Notification notification = Notification.builder().id(1L).message("Test").build();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        Optional<Notification> result = service.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getMessage()).isEqualTo("Test");
    }

    @Test
    public void testGetAll() {
        List<Notification> notifications = Arrays.asList(
            Notification.builder().id(1L).build(),
            Notification.builder().id(2L).build()
        );
        when(notificationRepository.findAll()).thenReturn(notifications);

        List<Notification> result = service.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void testUpdate() {
        Notification existing = Notification.builder().id(1L).readFlag(false).build();
        Notification updated = Notification.builder().id(1L).readFlag(true).build();

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(notificationRepository.save(any())).thenReturn(updated);

        Notification result = service.update(1L, updated);

        assertThat(result.getReadFlag()).isTrue();
    }

    @Test
    public void testDelete() {
        doNothing().when(notificationRepository).deleteById(1L);

        service.delete(1L);

        verify(notificationRepository).deleteById(1L);
    }
}

