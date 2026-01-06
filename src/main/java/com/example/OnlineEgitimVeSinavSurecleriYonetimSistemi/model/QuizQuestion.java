package com.example.OnlineEgitimVeSinavSurecleriYonetimSistemi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quiz_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String questionText;

    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;

    private String correctChoice;

    private Integer points = 1;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}

