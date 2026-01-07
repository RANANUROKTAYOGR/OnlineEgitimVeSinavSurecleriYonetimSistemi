package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.controller;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = User.builder().id(1L).email("test1@test.com").build();
        User user2 = User.builder().id(2L).email("test2@test.com").build();

        when(userService.getAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("test1@test.com"));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = User.builder().id(1L).email("test@test.com").build();

        when(userService.getById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = User.builder().id(1L).email("new@test.com").build();

        when(userService.create(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"new@test.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("new@test.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = User.builder().id(1L).email("updated@test.com").build();

        when(userService.update(eq(1L), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"updated@test.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@test.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}

