package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.UserRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(Long id, User user) {
        return userRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(user, existing, "id");
            return userRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}

