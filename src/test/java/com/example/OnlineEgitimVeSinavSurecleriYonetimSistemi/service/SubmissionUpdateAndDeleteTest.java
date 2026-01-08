package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubmissionUpdateAndDeleteTest {
    SubmissionRepository repo;
    SubmissionServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(SubmissionRepository.class);
        service = new SubmissionServiceImpl(repo);
    }

    @Test
    void update_existing_submission_copiesProperties() {
        Submission existing = new Submission(); existing.setId(1L); existing.setScore(50.0);
        Submission updated = new Submission(); updated.setScore(90.0);
        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        Submission res = service.update(1L, updated);
        assertEquals(90.0, res.getScore());
    }

    @Test
    void update_missing_throws() {
        when(repo.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(2L, new Submission()));
    }

    @Test
    void delete_callsRepository() {
        doNothing().when(repo).deleteById(3L);
        service.delete(3L);
        verify(repo, times(1)).deleteById(3L);
    }
}

