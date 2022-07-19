package com.wardenfar.mediscreen.assess.controller;

import com.wardenfar.mediscreen.assess.model.AssessResult;
import com.wardenfar.mediscreen.assess.service.AssessService;
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
    public ResponseEntity<String> assessById(Integer patientId) {
        try {
            AssessResult result = assessService.assessPatientById(patientId);
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "assess/familyName", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        public ResponseEntity<String> assessByFamilyName(String familyName) {
        try {
            AssessResult result = assessService.assessPatientByFamilyName(familyName);
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
