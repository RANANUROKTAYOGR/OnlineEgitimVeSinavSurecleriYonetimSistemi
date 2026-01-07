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

    @Test
    public void findAll_returnsAllUsers() {
        userRepository.deleteAll();
        User u1 = User.builder().username("user1").email("u1@x.com").password("p").build();
        User u2 = User.builder().username("user2").email("u2@x.com").password("p").build();
        userRepository.save(u1);
        userRepository.save(u2);

        var users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }

    @Test
    public void deleteUser_removesFromDatabase() {
        User u = User.builder().username("delete").email("del@x.com").password("p").build();
        u = userRepository.save(u);
        Long id = u.getId();

        userRepository.delete(u);

        assertThat(userRepository.findById(id)).isEmpty();
    }

    @Test
    public void updateUser_updatesFields() {
        User u = User.builder().username("old").email("old@x.com").password("p").build();
        u = userRepository.save(u);

        u.setUsername("new");
        u.setEmail("new@x.com");
        userRepository.save(u);

        var updated = userRepository.findById(u.getId()).orElseThrow();
        assertThat(updated.getUsername()).isEqualTo("new");
        assertThat(updated.getEmail()).isEqualTo("new@x.com");
    }
}
