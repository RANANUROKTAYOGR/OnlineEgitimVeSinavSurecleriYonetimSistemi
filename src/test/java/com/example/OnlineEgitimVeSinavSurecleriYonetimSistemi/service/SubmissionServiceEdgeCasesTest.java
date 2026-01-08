package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.SubmissionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class SubmissionServiceEdgeCasesTest {
    SubmissionRepository repo;
    SubmissionServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(SubmissionRepository.class);
        service = new SubmissionServiceImpl(repo);
    }

    @Test
    void getAll_returns_empty_list_when_repo_empty() {
        when(repo.findAll()).thenReturn(List.of());
        assertTrue(service.getAll().isEmpty());
    }

    @Test
    void getAll_returns_list_when_repo_has_entries() {
        Submission s1 = Submission.builder().id(1L).score(10.0).submittedAt(LocalDateTime.now()).build();
        Submission s2 = Submission.builder().id(2L).score(20.0).submittedAt(LocalDateTime.now()).build();
        when(repo.findAll()).thenReturn(List.of(s1, s2));
        List<Submission> all = service.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(s1) && all.contains(s2));
    }

    @Test
    void create_returns_saved_instance() {
        Submission s = new Submission(); s.setId(55L);
        when(repo.save(s)).thenReturn(s);
        assertEquals(s, service.create(s));
    }

    @Test
    void getById_wraps_optional() {
        Submission s = new Submission(); s.setId(66L);
        when(repo.findById(66L)).thenReturn(Optional.of(s));
        Optional<Submission> opt = service.getById(66L);
        assertTrue(opt.isPresent());
        assertEquals(66L, opt.get().getId());
    }

    @Test
    void getById_returns_empty_when_not_found() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Submission> out = service.getById(999L);
        assertFalse(out.isPresent());
    }

    @Test
    void update_success_updates_existing_and_returns_it() {
        Submission existing = Submission.builder()
                .id(1L)
                .score(5.0)
                .submittedAt(LocalDateTime.of(2020,1,1,0,0))
                .build();

        Submission update = Submission.builder()
                .id(999L) // should be ignored by update
                .score(95.5)
                .submittedAt(LocalDateTime.of(2021,6,1,12,0))
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        // repo.save should return the entity that was saved (existing after copy)
        when(repo.save(any(Submission.class))).thenAnswer(inv -> inv.getArgument(0));

        Submission result = service.update(1L, update);

        // id must remain original
        assertEquals(1L, result.getId());
        // properties should be copied from update
        assertEquals(95.5, result.getScore());
        assertEquals(LocalDateTime.of(2021,6,1,12,0), result.getSubmittedAt());

        verify(repo).findById(1L);
        verify(repo).save(existing);
    }

    @Test
    void update_throws_when_not_found() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(42L, new Submission()));
        assertTrue(ex.getMessage().toLowerCase().contains("submission not found"));
    }

    @Test
    void delete_calls_repository_deleteById() {
        doNothing().when(repo).deleteById(123L);
        service.delete(123L);
        verify(repo, times(1)).deleteById(123L);
    }
}
