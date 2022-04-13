package com.wardenfar.mediscreen.patient.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @Id
    @Setter(value = AccessLevel.PRIVATE)
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Enumerated
    @Column(nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private LocalDate ddn;

    @Column
    private String adresse;

    @Column
    private String phone;

    public enum Genre {
        M,
        F
    }
}
