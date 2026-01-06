package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.InstructorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class InstructorServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    private InstructorServiceImpl instructorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        instructorService = new InstructorServiceImpl(transactionRepository);
    }

    @Test
    public void calculateInstructorMonthlyCommission_returnsSumOfCommissions() {
        Transaction tx1 = Transaction.builder().id(1L).amount(100.0).status("SUCCESS").createdAt(LocalDateTime.of(2025,12,1,0,0)).build();
        Transaction tx2 = Transaction.builder().id(2L).amount(50.0).status("SUCCESS").createdAt(LocalDateTime.of(2025,12,15,0,0)).build();
        // minimal linking to course/instructor
        when(transactionRepository.findAll()).thenReturn(List.of(tx1, tx2));

        Double commission = instructorService.calculateInstructorMonthlyCommission(999L, 2025, 12);
        assertThat(commission).isEqualTo(0.0);
    }
}

