package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubmissionServiceBasicTest {
    SubmissionRepository repo;
    SubmissionServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(SubmissionRepository.class);
        service = new SubmissionServiceImpl(repo);
    }

    @Test
    void create_and_getAll_and_findById() {
        Submission s = new Submission(); s.setId(1L); s.setScore(80.0);
        when(repo.save(s)).thenReturn(s);
        when(repo.findAll()).thenReturn(List.of(s));
        when(repo.findById(1L)).thenReturn(Optional.of(s));
        Submission c = service.create(s);
        assertEquals(80.0, c.getScore());
        assertEquals(Optional.of(s), service.getById(1L));
    }
}

