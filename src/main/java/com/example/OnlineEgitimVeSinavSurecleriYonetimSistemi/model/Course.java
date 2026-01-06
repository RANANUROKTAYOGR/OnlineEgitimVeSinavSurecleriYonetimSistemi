package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private Double price;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    private Double instructorCommissionPercent = 70.0;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Module> modules;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "course")
    private Set<Certificate> certificates;
}
