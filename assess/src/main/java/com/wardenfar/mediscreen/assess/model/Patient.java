package com.wardenfar.mediscreen.assess.model;

import lombok.*;

import java.time.LocalDate;

@Data
public class Patient {

    private Long id;
    private String family;
    private String given;
    private Sex sex;
    private LocalDate dob;
    private String address;
    private String phone;

    public enum Sex {
        M,
        F
    }
}
