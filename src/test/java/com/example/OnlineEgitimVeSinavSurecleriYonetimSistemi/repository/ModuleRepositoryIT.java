package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
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
