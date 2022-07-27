package com.wardenfar.mediscreen.assess.controller;

import com.wardenfar.mediscreen.assess.client.HistoricClient;
import com.wardenfar.mediscreen.assess.client.PatientClient;
import com.wardenfar.mediscreen.assess.model.AddNotesModel;
import com.wardenfar.mediscreen.assess.model.AssessLevel;
import com.wardenfar.mediscreen.assess.model.Patient;
import com.wardenfar.mediscreen.assess.model.PatientModel;
import com.wardenfar.mediscreen.assess.service.AssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Ce controller ne sera pas actif en production
 */
@RestController
public class TestController {

    @Autowired
    AssessService assessService;

    @Autowired
    PatientClient patientClient;

    @Autowired
    HistoricClient historicClient;

    @GetMapping(value = "/test")
    public ResponseEntity<String> test() {
        PatientModel noneModel = new PatientModel("TestNone", "Test", LocalDate.of(1966, 12, 31), Patient.Sex.F,
                "1 " + "Brookside St", "100-222-3333");
        PatientModel borderlineModel = new PatientModel("TestBorderline", "Test", LocalDate.of(1945, 6, 24),
                Patient.Sex.M, "1 " + "Brookside St", "100-222-3333");
        PatientModel inDangerModel = new PatientModel("TestInDanger", "Test", LocalDate.of(2004, 6, 18),
                Patient.Sex.M, "1 " + "Brookside St", "100-222-3333");
        PatientModel earlyOnsetModel = new PatientModel("TestEarlyOnset", "Test", LocalDate.of(2002, 6, 28),
                Patient.Sex.F, "1 " + "Brookside St", "100-222-3333");

        Integer noneId = patientClient.add(noneModel);
        Integer borderlineId = patientClient.add(borderlineModel);
        Integer inDangerId = patientClient.add(inDangerModel);
        Integer earlyOnsetId = patientClient.add(earlyOnsetModel);

        historicClient.add(new AddNotesModel(noneId, "Patient: TestNone Practitioner's notes/recommendations: " +
                "Patient" + " states that they are 'feeling terrific' Weight at or below recommended level"));
        historicClient.add(new AddNotesModel(borderlineId, "Patient: TestBorderline Practitioner's " + "notes" +
                "/recommendations: Patient states that they are feeling a great deal of stress at work Patient " +
                "also complains that their hearing seems Abnormal as of late"));
        historicClient.add(new AddNotesModel(borderlineId, "Patient: TestBorderline Practitioner's " + "notes" +
                "/recommendations: Patient states that they have had a Reaction to medication within last 3 " +
                "months Patient also complains that their hearing continues to be problematic"));
        historicClient.add(new AddNotesModel(inDangerId, "Patient: TestInDanger Practitioner's " +
                "notes/recommendations:" + " Patient states that they are short term Smoker "));
        historicClient.add(new AddNotesModel(inDangerId, "Patient: TestInDanger Practitioner's " +
                "notes/recommendations:" + " Patient states that they quit within last year Patient also complains " +
                "that of Abnormal breathing " + "spells Lab reports Cholesterol LDL high"));
        historicClient.add(new AddNotesModel(earlyOnsetId, "Patient: TestEarlyOnset Practitioner's " + "notes" +
                "/recommendations: Patient states that walking up stairs has become difficult Patient also " +
                "complains that they are having shortness of breath Lab results indicate Antibodies present elevated "
                + "Reaction to medication"));
        historicClient.add(new AddNotesModel(earlyOnsetId, "Patient: TestEarlyOnset Practitioner's " + "notes" +
                "/recommendations: Patient states that they are experiencing back pain when seated for a long " +
                "time"));
        historicClient.add(new AddNotesModel(earlyOnsetId, "Patient: TestEarlyOnset Practitioner's " + "notes" +
                "/recommendations: Patient states that they are a short term Smoker Hemoglobin A1C above " +
                "recommended level"));
        historicClient.add(new AddNotesModel(earlyOnsetId, "Patient: TestEarlyOnset Practitioner's " + "notes" +
                "/recommendations: Patient states that Body Height, Body Weight, Cholesterol, Dizziness and " +
                "Reaction"));

        assert AssessLevel.None == assessService.assessPatientById(noneId).level;
        assert AssessLevel.InDanger == assessService.assessPatientById(inDangerId).level;
        assert AssessLevel.Borderline == assessService.assessPatientById(borderlineId).level;
        assert AssessLevel.EarlyOnset == assessService.assessPatientById(earlyOnsetId).level;

        return ResponseEntity.ok("OK");
    }
}
