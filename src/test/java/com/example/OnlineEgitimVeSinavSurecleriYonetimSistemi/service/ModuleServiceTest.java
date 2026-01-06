package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.ModuleRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.ModuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ModuleServiceTest {
    @Mock
    private ModuleRepository moduleRepository;
    private ModuleServiceImpl moduleService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        moduleService = new ModuleServiceImpl(moduleRepository);
    }

    @Test
    public void createModule_savesAndReturns(){
        Module m = Module.builder().title("M1").position(1).build();
        when(moduleRepository.save(m)).thenReturn(Module.builder().id(1L).title("M1").position(1).build());
        var saved = moduleService.create(m);
        assertThat(saved.getId()).isEqualTo(1L);
    }
}
