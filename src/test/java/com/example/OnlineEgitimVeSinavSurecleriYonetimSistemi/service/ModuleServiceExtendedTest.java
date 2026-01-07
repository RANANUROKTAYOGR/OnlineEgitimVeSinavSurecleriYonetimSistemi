package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.ModuleRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.ModuleServiceImpl;
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

public class ModuleServiceExtendedTest {
    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById() {
        Module module = Module.builder().id(1L).title("Module 1").build();
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));

        Optional<Module> result = service.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Module 1");
    }

    @Test
    public void testGetAll() {
        List<Module> modules = Arrays.asList(
            Module.builder().id(1L).build(),
            Module.builder().id(2L).build()
        );
        when(moduleRepository.findAll()).thenReturn(modules);

        List<Module> result = service.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    public void testUpdate() {
        Module existing = Module.builder().id(1L).title("Old").build();
        Module updated = Module.builder().id(1L).title("New").build();

        when(moduleRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(moduleRepository.save(any())).thenReturn(updated);

        Module result = service.update(1L, updated);

        assertThat(result.getTitle()).isEqualTo("New");
    }

    @Test
    public void testDelete() {
        doNothing().when(moduleRepository).deleteById(1L);

        service.delete(1L);

        verify(moduleRepository).deleteById(1L);
    }
}

