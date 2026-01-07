package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.UserRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void createUser_savesAndReturns() {
        User u = User.builder().username("u").email("e@a").password("p").build();
        when(userRepository.save(u)).thenReturn(User.builder().id(2L).username("u").email("e@a").build());
        var saved = userService.create(u);
        assertThat(saved.getId()).isEqualTo(2L);
    }

    @Test
    public void getById_notFound_returnsEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        var got = userService.getById(99L);
        assertThat(got).isEmpty();
    }

    @Test
    public void getById_found_returnsUser() {
        User user = User.builder().id(1L).username("test").email("test@test.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        Optional<User> result = userService.getById(1L);
        
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("test");
    }

    @Test
    public void getAll_returnsAllUsers() {
        List<User> users = Arrays.asList(
            User.builder().id(1L).username("user1").build(),
            User.builder().id(2L).username("user2").build(),
            User.builder().id(3L).username("user3").build()
        );
        when(userRepository.findAll()).thenReturn(users);
        
        List<User> result = userService.getAll();
        
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getUsername()).isEqualTo("user1");
    }

    @Test
    public void update_updatesExistingUser() {
        User existing = User.builder().id(1L).username("old").email("old@test.com").build();
        User updated = User.builder().id(1L).username("new").email("new@test.com").build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(updated);
        
        User result = userService.update(1L, updated);
        
        assertThat(result.getUsername()).isEqualTo("new");
        assertThat(result.getEmail()).isEqualTo("new@test.com");
    }

    @Test
    public void delete_deletesUser() {
        doNothing().when(userRepository).deleteById(1L);
        
        userService.delete(1L);
        
        verify(userRepository, times(1)).deleteById(1L);
    }
}

