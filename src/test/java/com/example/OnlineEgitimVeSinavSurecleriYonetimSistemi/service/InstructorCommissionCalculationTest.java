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

public class InstructorCommissionCalculationTest {
    TransactionRepository transactionRepository;
    InstructorServiceImpl service;

    @BeforeEach
    void setup() {
        transactionRepository = mock(TransactionRepository.class);
        service = new InstructorServiceImpl(transactionRepository);
    }

    @Test
    void calculateCommission_filtersByStatusAndDateAndInstructor() {
        User instructor = new User(); instructor.setId(50L);
        Course course = new Course(); course.setInstructor(instructor); course.setInstructorCommissionPercent(10.0);
        Enrollment enrollment = new Enrollment(); enrollment.setCourse(course);
        Transaction t1 = new Transaction(); t1.setStatus("SUCCESS"); t1.setEnrollment(enrollment); t1.setAmount(200.0);
        t1.setCreatedAt(LocalDateTime.of(2025, 12, 15, 0,0));
        Transaction t2 = new Transaction(); t2.setStatus("FAILED"); t2.setEnrollment(enrollment); t2.setAmount(100.0);
        t2.setCreatedAt(LocalDateTime.of(2025, 12, 16, 0,0));
        // another instructor
        User otherInst = new User(); otherInst.setId(99L);
        Course otherCourse = new Course(); otherCourse.setInstructor(otherInst); otherCourse.setInstructorCommissionPercent(50.0);
        Enrollment otherEnrollment = new Enrollment(); otherEnrollment.setCourse(otherCourse);
        Transaction t3 = new Transaction(); t3.setStatus("SUCCESS"); t3.setEnrollment(otherEnrollment); t3.setAmount(300.0);
        t3.setCreatedAt(LocalDateTime.of(2025, 12, 20, 0,0));

        when(transactionRepository.findAll()).thenReturn(List.of(t1, t2, t3));

        double commission = service.calculateInstructorMonthlyCommission(50L, 2025, 12);
        // only t1 counts: 200 * 10% = 20
        assertEquals(20.0, commission, 0.0001);
    }

    @Test
    void calculateCommission_handlesNullsGracefully() {
        when(transactionRepository.findAll()).thenReturn(List.of(new Transaction(), new Transaction()));
        assertEquals(0.0, service.calculateInstructorMonthlyCommission(1L, 2025, 1));
    }
}

