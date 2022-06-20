package com.wardenfar.mediscreen.patient.controller;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.error.NotFoundException;
import com.wardenfar.mediscreen.patient.model.PatientModel;
import com.wardenfar.mediscreen.patient.model.AddRecordResponse;
import com.wardenfar.mediscreen.patient.service.PatientService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class ApiPatientController {

    final PatientService patientService;

    public ApiPatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AddRecordResponse add(PatientModel model) {
        try {
            Long id = patientService.addPatient(model);
            return new AddRecordResponse(true, id);
        } catch (Exception e) {
            return new AddRecordResponse(false, null);
        }
    }

    @GetMapping("/fetch")
    public Patient fetch(@RequestParam Long id) {
        Optional<Patient> patient = patientService.findById(id);
        if (patient.isEmpty()) {
            throw new NotFoundException("Patient not found");
        } else {
            return patient.get();
        }
    }
}
