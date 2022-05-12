package com.wardenfar.mediscreen.patient.controller;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.error.NotFoundException;
import com.wardenfar.mediscreen.patient.model.PatientModel;
import com.wardenfar.mediscreen.patient.service.PatientService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PatientController {

    final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/view/{id}")
    public String add(@PathVariable Long id, Model model) {
        Optional<Patient> patient = patientService.findById(id);
        if (patient.isEmpty()) {
            throw new NotFoundException("Patient not found");
        } else {
            model.addAttribute("patient", patient.get());
            return "view";
        }
    }

    @PostMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(@PathVariable Long id, @Valid PatientModel patientModel) {
        patientService.updatePatient(id, patientModel);
        return "redirect:/view/" + id;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "list";
    }
}
