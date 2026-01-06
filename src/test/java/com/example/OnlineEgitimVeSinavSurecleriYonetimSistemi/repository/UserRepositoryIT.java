package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser_findById_returnsUser() {
        User u = User.builder().username("john").email("j@x.com").password("p").build();
        u = userRepository.save(u);
        var fetched = userRepository.findById(u.getId()).orElse(null);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getUsername()).isEqualTo("john");
    }
}
