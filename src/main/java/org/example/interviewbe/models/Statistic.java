package org.example.interviewbe.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "statistic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="mon_hoc", nullable = false, unique = true)
    String subject;

    @Column(name="group1", nullable = false)
    int group1;

    @Column(name="group2", nullable = false)
    int group2;

    @Column(name="group3", nullable = false)
    int group3;

    @Column(name="group4", nullable = false)
    int group4;
}
