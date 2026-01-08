package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.ModuleRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.ModuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModuleServiceCrudTest {
    ModuleRepository repo;
    ModuleServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(ModuleRepository.class);
        service = new ModuleServiceImpl(repo);
    }

    @Test
    void simple_crud() {
        Module m = new Module(); m.setId(2L); m.setTitle("M");
        when(repo.save(m)).thenReturn(m);
        when(repo.findAll()).thenReturn(List.of(m));
        when(repo.findById(2L)).thenReturn(Optional.of(m));
        Module created = service.create(m);
        assertEquals(m, created);
        assertEquals(1, service.getAll().size());
        assertTrue(service.getById(2L).isPresent());
    }

    @Test
    void update_throws_when_missing() {
        when(repo.findById(10L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(10L, new Module()));
    }
}

