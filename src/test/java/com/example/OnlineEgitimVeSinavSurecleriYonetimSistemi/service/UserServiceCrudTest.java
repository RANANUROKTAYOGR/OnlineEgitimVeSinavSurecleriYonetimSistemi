package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.UserRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceCrudTest {
    UserRepository repository;
    UserServiceImpl service;

    @BeforeEach
    void setup() {
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository);
    }

    @Test
    void create_and_getAll_and_findById() {
        User u = new User(); u.setId(1L); u.setUsername("alice");
        when(repository.save(u)).thenReturn(u);
        when(repository.findAll()).thenReturn(List.of(u));
        when(repository.findById(1L)).thenReturn(Optional.of(u));

        User created = service.create(u);
        assertEquals("alice", created.getUsername());
        assertTrue(service.getAll().contains(u));
        assertEquals(u, service.findById(1L));
    }

    @Test
    void findById_throws_when_missing() {
        when(repository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(5L));
    }

    @Test
    void update_and_delete() {
        User existing = new User(); existing.setId(2L); existing.setUsername("bob");
        User updated = new User(); updated.setUsername("bobby");
        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        User res = service.update(2L, updated);
        assertEquals("bobby", res.getUsername());

        doNothing().when(repository).deleteById(2L);
        service.delete(2L);
        verify(repository).deleteById(2L);
    }
}

