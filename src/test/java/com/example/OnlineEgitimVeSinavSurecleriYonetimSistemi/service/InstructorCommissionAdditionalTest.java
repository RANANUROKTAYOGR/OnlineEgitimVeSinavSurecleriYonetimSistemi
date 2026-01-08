package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.*;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.InstructorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InstructorCommissionAdditionalTest {
    TransactionRepository transactionRepository;
    InstructorServiceImpl service;

    @BeforeEach
    void setup() {
        transactionRepository = mock(TransactionRepository.class);
        service = new InstructorServiceImpl(transactionRepository);
    }

    @Test
    void commission_is_zero_when_no_transactions() {
        when(transactionRepository.findAll()).thenReturn(List.of());
        double c = service.calculateInstructorMonthlyCommission(1L, 2026, 1);
        assertEquals(0.0, c);
    }

    @Test
    void commission_calculated_only_for_success_transactions_and_matching_instructor_and_month() {
        User instructor = new User(); instructor.setId(5L);
        Course course = new Course(); course.setInstructor(instructor); course.setInstructorCommissionPercent(10.0);
        Enrollment enrollment = new Enrollment(); enrollment.setCourse(course);

        Transaction t1 = new Transaction(); t1.setStatus("SUCCESS"); t1.setEnrollment(enrollment); t1.setAmount(100.0); t1.setCreatedAt(LocalDateTime.of(2026,1,15,0,0));
        Transaction t2 = new Transaction(); t2.setStatus("FAILED"); t2.setEnrollment(enrollment); t2.setAmount(200.0); t2.setCreatedAt(LocalDateTime.of(2026,1,15,0,0));
        Transaction t3 = new Transaction(); t3.setStatus("SUCCESS"); t3.setEnrollment(enrollment); t3.setAmount(50.0); t3.setCreatedAt(LocalDateTime.of(2026,2,1,0,0));

        when(transactionRepository.findAll()).thenReturn(List.of(t1, t2, t3));
        double c = service.calculateInstructorMonthlyCommission(5L, 2026, 1);
        assertEquals(10.0, c); // only t1 counted: 100 * 10% = 10
    }

    @Test
    void commission_handles_nulls_gracefully() {
        Transaction t = new Transaction(); t.setStatus(null);
        when(transactionRepository.findAll()).thenReturn(List.of(t));
        double c = service.calculateInstructorMonthlyCommission(1L, 2026, 1);
        assertEquals(0.0, c);
    }
}

