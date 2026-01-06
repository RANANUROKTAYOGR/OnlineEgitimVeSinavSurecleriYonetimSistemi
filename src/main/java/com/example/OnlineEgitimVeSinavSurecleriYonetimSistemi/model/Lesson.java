package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String content;

    private Integer position;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}

