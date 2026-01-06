package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Enrollment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EnrollmentRepositoryIT {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    public void saveEnrollment_andFind() {
        Enrollment e = Enrollment.builder().active(true).build();
        e = enrollmentRepository.save(e);
        var got = enrollmentRepository.findById(e.getId()).orElse(null);
        assertThat(got).isNotNull();
    }
}
