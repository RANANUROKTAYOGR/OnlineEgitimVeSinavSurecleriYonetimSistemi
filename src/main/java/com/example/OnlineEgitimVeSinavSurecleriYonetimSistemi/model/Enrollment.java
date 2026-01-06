package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDateTime enrolledAt;

    private Boolean active = true;

    // Snapshot fields to track progress without joining lots of tables
    private Integer completedLessons = 0;

    private Integer totalLessonsSnapshot = 0;
}
