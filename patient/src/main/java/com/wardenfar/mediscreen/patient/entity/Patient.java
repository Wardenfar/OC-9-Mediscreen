package com.wardenfar.mediscreen.patient.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
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
    private String family;

    @Column(nullable = false)
    private String given;

    @Enumerated
    @Column(nullable = false)
    private Sex sex;

    @Column(nullable = false)
    private LocalDate dob;

    @Column
    private String address;

    @Column
    private String phone;

    public enum Sex {
        M,
        F
    }

    @JsonGetter
    private boolean isSexM() {
        return this.sex == Sex.M;
    }

    @JsonGetter
    private boolean isSexF() {
        return this.sex == Sex.F;
    }
}
