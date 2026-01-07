package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Module;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.ModuleRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public Module create(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> getById(Long id) {
        return moduleRepository.findById(id);
    }

    @Override
    public Module findById(Long id) {
        return moduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));
    }

    @Override
    public List<Module> getAll() {
        return moduleRepository.findAll();
    }

    @Override
    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    @Override
    public Module update(Long id, Module module) {
        return moduleRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(module, existing, "id");
            return moduleRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Module not found"));
    }

    @Override
    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }
}

