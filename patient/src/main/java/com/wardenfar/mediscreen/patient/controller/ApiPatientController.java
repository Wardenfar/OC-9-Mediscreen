package com.wardenfar.mediscreen.patient.controller;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.error.NotFoundException;
import com.wardenfar.mediscreen.patient.model.AddPatientModel;
import com.wardenfar.mediscreen.patient.model.AddRecordResponse;
import com.wardenfar.mediscreen.patient.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ApiPatientController {

    final PatientService patientService;

    public ApiPatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/patient")
    public AddRecordResponse add(@RequestBody AddPatientModel model) {
        try {
            Long id = patientService.addPatient(model);
            return new AddRecordResponse(true, id);
        } catch (Exception e) {
            return new AddRecordResponse(false, null);
        }
    }

    @GetMapping("/patient")
    public Patient fetch(@RequestParam Long id) {
        Optional<Patient> patient = patientService.findById(id);
        if (patient.isEmpty()) {
            throw new NotFoundException("Patient not found");
        } else {
            return patient.get();
        }
    }
}
