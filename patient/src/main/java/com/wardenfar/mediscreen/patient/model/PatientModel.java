package com.wardenfar.mediscreen.patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wardenfar.mediscreen.patient.entity.Patient;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class PatientModel {
    
    String family;

    String given;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;

    Patient.Sex sex;

    String address;

    String phone;

    public void updateEntity(Patient patient) {
        patient.setFamily(this.getFamily());
        patient.setGiven(this.getGiven());
        patient.setAddress(this.getAddress());
        patient.setSex(this.getSex());
        patient.setDob(this.getDob());
        patient.setPhone(this.getPhone());
    }
}
