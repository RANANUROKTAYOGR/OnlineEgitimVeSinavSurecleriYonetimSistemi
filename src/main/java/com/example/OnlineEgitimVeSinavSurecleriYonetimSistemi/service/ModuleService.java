package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;

import java.util.List;
import java.util.Optional;

public interface ModuleService {
    Module create(Module module);
    Optional<Module> getById(Long id);
    List<Module> getAll();
    Module update(Long id, Module module);
    void delete(Long id);

    // Convenience method aliases
    default List<Module> findAll() {
        return getAll();
    }

    default Optional<Module> findById(Long id) {
        return getById(id);
    }
}

