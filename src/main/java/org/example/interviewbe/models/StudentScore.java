package org.example.interviewbe.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Table(name="score",
        indexes = {
                @Index(name = "idx_registration_num", columnList = "registration_number"),
                @Index(name = "idx_total_A", columnList = "total_A")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "registration_number", nullable = false, unique = true)

    String registrationNumber;

    @Column(name="maths", nullable = false)
    Float math;

    @Column(name="literature", nullable = false)
    Float literature;

    @Column(name="language", nullable = false)
    Float language;

    @Column(name="physics")
    Float physics;

    @Column(name="chemistry")
    Float chemistry;

    @Column(name="biology")
    Float biology;

    @Column(name="history")
    Float history;

    @Column(name="geography")
    Float geography;

    @Column(name="gdcd")
    Float gdcd;

    @Column(name="total_A")
    Float totalA;

    @Column(name="language_code", nullable = false)
    String languageCode;
}
