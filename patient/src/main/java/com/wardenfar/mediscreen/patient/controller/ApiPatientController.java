package com.wardenfar.mediscreen.patient.controller;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.error.NotFoundException;
import com.wardenfar.mediscreen.patient.model.PatientModel;
import com.wardenfar.mediscreen.patient.service.PatientService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> add(PatientModel model) {
        try {
            Long id = patientService.addPatient(model);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<Patient> fetch(@RequestParam Long id) {
        Optional<Patient> patient = patientService.findById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
