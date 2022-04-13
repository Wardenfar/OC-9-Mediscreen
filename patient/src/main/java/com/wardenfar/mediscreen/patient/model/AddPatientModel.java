package com.wardenfar.mediscreen.patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wardenfar.mediscreen.patient.entity.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AddPatientModel {
    
    String family;

    String given;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dob;

    Patient.Genre sex;

    String address;

    String phone;
}
