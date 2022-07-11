package com.wardenfar.mediscreen.patient.controller;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.model.PatientModel;
import com.wardenfar.mediscreen.patient.service.PatientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation("Ajoute un patient dans la base de données")
    @ApiResponses({
            @ApiResponse(code = 200, message = "L'ajout est un succès"),
            @ApiResponse(code = 400, message = "L'ajout n'a pas abouti")
    })
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Long> add(@ApiParam("Données du patient") PatientModel model) {
        try {
            Long id = patientService.addPatient(model);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Récupère les données d'un patient avec son ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Le patient a été trouvé"),
            @ApiResponse(code = 400, message = "Le patient n'a pas été trouvé")
    })
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Patient> fetch(@ApiParam("ID du patient") @PathVariable Long id) {
        Optional<Patient> patient = patientService.findById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
