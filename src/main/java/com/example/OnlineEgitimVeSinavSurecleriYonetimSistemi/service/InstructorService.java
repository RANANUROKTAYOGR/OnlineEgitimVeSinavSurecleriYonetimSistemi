package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

public interface InstructorService {
    /**
     * Calculate instructor commission for given month (year, month) summed across all courses
     */
    Double calculateInstructorMonthlyCommission(Long instructorId, int year, int month);
}

