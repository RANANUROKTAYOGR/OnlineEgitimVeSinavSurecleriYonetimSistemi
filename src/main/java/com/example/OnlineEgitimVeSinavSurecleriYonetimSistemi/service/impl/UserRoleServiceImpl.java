package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.UserRole;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.UserRoleRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole create(UserRole role) {
        return userRoleRepository.save(role);
    }
}

