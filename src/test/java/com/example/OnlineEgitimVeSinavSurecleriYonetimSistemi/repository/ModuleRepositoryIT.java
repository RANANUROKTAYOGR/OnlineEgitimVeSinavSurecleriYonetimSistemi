package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ModuleRepositoryIT {
    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    public void saveModule_andFetch() {
        Module m = Module.builder().title("mod").position(1).build();
        m = moduleRepository.save(m);
        var got = moduleRepository.findById(m.getId()).orElse(null);
        assertThat(got).isNotNull();
    }
}
