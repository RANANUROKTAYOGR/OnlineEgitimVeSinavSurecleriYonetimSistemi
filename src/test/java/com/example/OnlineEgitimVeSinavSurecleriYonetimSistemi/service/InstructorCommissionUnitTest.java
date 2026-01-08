package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Course;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Transaction;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.User;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository.TransactionRepository;
import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.service.impl.InstructorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InstructorCommissionUnitTest {

    @Test
    void calculateInstructorMonthlyCommission_countsOnlyMatchingMonthAndStatus() {
        TransactionRepository txRepo = Mockito.mock(TransactionRepository.class);
        InstructorServiceImpl service = new InstructorServiceImpl(txRepo);

        var instructor = new User();
        instructor.setId(900L);

        Course course = new Course();
        course.setInstructor(instructor);
        course.setInstructorCommissionPercent(10.0);

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);

        Transaction tx1 = new Transaction();
        tx1.setStatus("SUCCESS");
        tx1.setEnrollment(enrollment);
        tx1.setAmount(100.0);
        tx1.setCreatedAt(LocalDateTime.of(2025, 12, 15, 10, 0));

        Transaction tx2 = new Transaction();
        tx2.setStatus("FAILED");
        tx2.setEnrollment(enrollment);
        tx2.setAmount(200.0);
        tx2.setCreatedAt(LocalDateTime.of(2025, 12, 16, 10, 0));

        Transaction tx3 = new Transaction();
        tx3.setStatus("SUCCESS");
        tx3.setEnrollment(enrollment);
        tx3.setAmount(50.0);
        tx3.setCreatedAt(LocalDateTime.of(2025, 11, 15, 10, 0));

        Mockito.when(txRepo.findAll()).thenReturn(List.of(tx1, tx2, tx3));

        double commission = service.calculateInstructorMonthlyCommission(900L, 2025, 12);
        // only tx1 qualifies: 100 * 10% = 10.0
        assertThat(commission).isEqualTo(10.0);
    }

    @Test
    void calculateInstructorMonthlyCommission_handlesMultipleCoursesAndAmounts() {
        TransactionRepository txRepo = Mockito.mock(TransactionRepository.class);
        InstructorServiceImpl service = new InstructorServiceImpl(txRepo);

        var instructor = new User();
        instructor.setId(1000L);

        Course c1 = new Course();
        c1.setInstructor(instructor);
        c1.setInstructorCommissionPercent(20.0);

        Course c2 = new Course();
        c2.setInstructor(instructor);
        c2.setInstructorCommissionPercent(10.0);

        Enrollment e1 = new Enrollment(); e1.setCourse(c1);
        Enrollment e2 = new Enrollment(); e2.setCourse(c2);

        Transaction t1 = new Transaction();
        t1.setStatus("SUCCESS"); t1.setEnrollment(e1); t1.setAmount(50.0); t1.setCreatedAt(LocalDateTime.of(2026,1,5,10,0));
        Transaction t2 = new Transaction();
        t2.setStatus("SUCCESS"); t2.setEnrollment(e2); t2.setAmount(100.0); t2.setCreatedAt(LocalDateTime.of(2026,1,10,10,0));

        Mockito.when(txRepo.findAll()).thenReturn(List.of(t1, t2));

        // commissions: t1 -> 50*20% = 10, t2 -> 100*10% = 10 => total 20
        double commission = service.calculateInstructorMonthlyCommission(1000L, 2026, 1);
        assertThat(commission).isEqualTo(20.0);
    }

    @Test
    void calculateInstructorMonthlyCommission_ignoresNullEnrollmentsAndDifferentInstructors() {
        TransactionRepository txRepo = Mockito.mock(TransactionRepository.class);
        InstructorServiceImpl service = new InstructorServiceImpl(txRepo);

        var instructor = new User(); instructor.setId(42L);
        var other = new User(); other.setId(99L);

        Course course = new Course(); course.setInstructor(instructor); course.setInstructorCommissionPercent(50.0);
        Course courseOther = new Course(); courseOther.setInstructor(other); courseOther.setInstructorCommissionPercent(50.0);

        Enrollment eOk = new Enrollment(); eOk.setCourse(course);
        Enrollment eOther = new Enrollment(); eOther.setCourse(courseOther);

        Transaction good = new Transaction(); good.setStatus("SUCCESS"); good.setEnrollment(eOk); good.setAmount(200.0); good.setCreatedAt(LocalDateTime.of(2025,6,1,1,1));
        Transaction wrongInstructor = new Transaction(); wrongInstructor.setStatus("SUCCESS"); wrongInstructor.setEnrollment(eOther); wrongInstructor.setAmount(1000.0); wrongInstructor.setCreatedAt(LocalDateTime.of(2025,6,2,1,1));
        Transaction nullEnroll = new Transaction(); nullEnroll.setStatus("SUCCESS"); nullEnroll.setEnrollment(null); nullEnroll.setAmount(500.0); nullEnroll.setCreatedAt(LocalDateTime.of(2025,6,3,1,1));

        Mockito.when(txRepo.findAll()).thenReturn(List.of(good, wrongInstructor, nullEnroll));

        double commission = service.calculateInstructorMonthlyCommission(42L, 2025, 6);
        // only `good` counts: 200 * 50% = 100
        assertThat(commission).isEqualTo(100.0);
    }
}
