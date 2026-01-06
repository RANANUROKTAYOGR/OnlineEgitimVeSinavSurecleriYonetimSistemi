package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.UserRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void createUser_savesAndReturns() {
        User u = User.builder().username("u").email("e@a").password("p").build();
        when(userRepository.save(u)).thenReturn(User.builder().id(2L).username("u").email("e@a").build());
        var saved = userService.create(u);
        assertThat(saved.getId()).isEqualTo(2L);
    }

    @Test
    public void getById_notFound_returnsEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        var got = userService.getById(99L);
        assertThat(got).isEmpty();
    }
}

