package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleService {
    Module create(Module module);
    Optional<Module> getById(Long id);
    Module findById(Long id);
    List<Module> getAll();
    List<Module> findAll();
    Module update(Long id, Module module);
    void delete(Long id);
}

