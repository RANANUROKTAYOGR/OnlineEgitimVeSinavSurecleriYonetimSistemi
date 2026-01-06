package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.repository;

import com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model.Submission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class SubmissionRepositoryIT {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Test
    public void saveSubmission_andRetrieve() {
        Submission s = Submission.builder().score(9.0).build();
        s = submissionRepository.save(s);
        var got = submissionRepository.findById(s.getId()).orElse(null);
        assertThat(got).isNotNull();
        assertThat(got.getScore()).isEqualTo(9.0);
    }
}
