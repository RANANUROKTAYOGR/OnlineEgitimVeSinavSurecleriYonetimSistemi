package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> getById(Long id);
    User findById(Long id);
    List<User> getAll();
    List<User> findAll();
    User update(Long id, User user);
    void delete(Long id);
}

