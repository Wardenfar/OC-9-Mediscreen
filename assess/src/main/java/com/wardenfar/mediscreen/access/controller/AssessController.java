package com.wardenfar.mediscreen.access.controller;

import com.wardenfar.mediscreen.access.model.AssessResult;
import com.wardenfar.mediscreen.access.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssessController {

    @Autowired
    AssessService assessService;

    @PostMapping(value = "assess/id", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> assess(Integer patientId) {
        try {
            AssessResult result = assessService.assessPatient(patientId);
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
