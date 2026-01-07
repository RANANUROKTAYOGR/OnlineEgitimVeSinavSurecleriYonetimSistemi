package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRoleRepositoryIT {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void saveRole_andFetch() {
        UserRole r = UserRole.builder().name("ROLE_ADMIN").build();
        r = userRoleRepository.save(r);
        var got = userRoleRepository.findById(r.getId()).orElse(null);
        assertThat(got).isNotNull();
    }
}
