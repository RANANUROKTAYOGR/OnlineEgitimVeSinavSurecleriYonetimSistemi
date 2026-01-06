package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserRoleServiceTest {
    @Mock
    private UserRoleService userRoleService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRole_saves() {
        UserRole r = UserRole.builder().name("ROLE_USER").build();
        when(userRoleService.create(any(UserRole.class))).thenReturn(UserRole.builder().id(1L).name("ROLE_USER").build());
        var saved = userRoleService.create(r);
        assertThat(saved.getId()).isEqualTo(1L);
    }
}
