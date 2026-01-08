package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.UserRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceNotFoundTest {
    UserRepository userRepository;
    UserServiceImpl service;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        service = new UserServiceImpl(userRepository);
    }

    @Test
    void findById_throws_when_user_missing() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findById(999L));
        assertTrue(ex.getMessage().toLowerCase().contains("user not found"));
    }

    @Test
    void update_throws_when_user_missing() {
        User sample = User.builder().username("a").email("a@b").build();
        when(userRepository.findById(5L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(5L, sample));
        assertTrue(ex.getMessage().toLowerCase().contains("user not found"));
    }
}

