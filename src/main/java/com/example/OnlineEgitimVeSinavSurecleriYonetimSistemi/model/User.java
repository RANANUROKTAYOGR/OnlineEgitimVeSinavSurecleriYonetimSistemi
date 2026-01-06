package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private Set<Enrollment> enrollments;

    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    private Set<Submission> submissions;

    @OneToMany(mappedBy = "user")
    private Set<Certificate> certificates;

    @OneToMany(mappedBy = "recipient")
    private Set<Notification> notifications;
}
