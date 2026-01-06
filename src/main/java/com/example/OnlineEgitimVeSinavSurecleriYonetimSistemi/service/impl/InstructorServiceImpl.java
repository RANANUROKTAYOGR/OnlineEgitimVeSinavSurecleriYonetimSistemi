package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.InstructorService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class InstructorServiceImpl implements InstructorService {
    private final TransactionRepository transactionRepository;

    public InstructorServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Double calculateInstructorMonthlyCommission(Long instructorId, int year, int month) {
        double totalCommission = 0.0;
        var txs = transactionRepository.findAll();
        for (var tx : txs) {
            if (tx.getStatus() == null) continue;
            if (!"SUCCESS".equalsIgnoreCase(tx.getStatus())) continue;
            var enrollment = tx.getEnrollment();
            if (enrollment == null) continue;
            var course = enrollment.getCourse();
            if (course == null || course.getInstructor() == null || course.getInstructor().getId() == null) continue;
            if (!course.getInstructor().getId().equals(instructorId)) continue;
            var created = tx.getCreatedAt();
            if (created == null) continue;
            LocalDateTime ldt = created;
            if (ldt.getYear() == year && ldt.getMonthValue() == month) {
                double percent = (course.getInstructorCommissionPercent() != null ? course.getInstructorCommissionPercent() : 0.0);
                totalCommission += (tx.getAmount() != null ? tx.getAmount() : 0.0) * (percent / 100.0);
            }
        }
        return totalCommission;
    }
}

