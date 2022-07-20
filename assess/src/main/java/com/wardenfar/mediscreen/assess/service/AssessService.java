package com.wardenfar.mediscreen.assess.service;

import com.wardenfar.mediscreen.assess.client.HistoricClient;
import com.wardenfar.mediscreen.assess.client.PatientClient;
import com.wardenfar.mediscreen.assess.model.AssessLevel;
import com.wardenfar.mediscreen.assess.model.AssessResult;
import com.wardenfar.mediscreen.assess.model.Historic;
import com.wardenfar.mediscreen.assess.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AssessService {

    @Autowired
    PatientClient patientClient;

    @Autowired
    HistoricClient historicClient;

    public static final String[] WARNING_TERMS = {
            // French
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Anormal",
            "Cholestérol",
            "Vertige",
            // English
            "Hemoglobin A1C",
            "Microalbumine",
            "Height",
            "Weight",
            "Smoker",
            "Abnormal",
            "Cholesterol",
            "Dizziness",
            "Reaction"
    };

    public AssessResult assessPatientById(Integer patientId) {
        Patient patient = patientClient.getById(patientId);
        return assessPatient(patient);
    }

    public AssessResult assessPatientByFamilyName(String familyName) {
        Patient patient = patientClient.getByFamilyName(familyName);
        return assessPatient(patient);
    }

    public AssessResult assessPatient(Patient patient) {
        Historic historic = historicClient.getByPatientId(patient.getId());

        int termsCount = countTerms(historic.getNotes(), WARNING_TERMS);
        int age = calculateAge(patient.getDob(), LocalDate.now());
        boolean isMale = patient.getSex() == Patient.Sex.M;
        AssessLevel level = calculateLevel(termsCount, age, isMale);

        AssessResult result = new AssessResult();
        result.setAge(age);
        result.setLevel(level);
        result.setGiven(patient.getGiven());
        result.setFamily(patient.getFamily());
        return result;
    }

    public AssessLevel calculateLevel(int termsCount, int age, boolean isMale) {
        AssessLevel level = AssessLevel.None;

        if (termsCount == 0) {
            level = AssessLevel.None;
        } else if (age < 30) {
            if (isMale) {
                if (termsCount >= 5) {
                    level = AssessLevel.EarlyOnset;
                } else if (termsCount >= 3) {
                    level = AssessLevel.InDanger;
                }
            } else {
                if (termsCount >= 7) {
                    level = AssessLevel.EarlyOnset;
                } else if (termsCount >= 4) {
                    level = AssessLevel.InDanger;
                }
            }
        } else { // age >= 30
            if (termsCount >= 8) {
                level = AssessLevel.EarlyOnset;
            } else if (termsCount >= 6) {
                level = AssessLevel.InDanger;
            } else if (termsCount >= 2) {
                level = AssessLevel.Borderline;
            }
        }
        return level;
    }

    public int countTerms(String text, String[] terms) {
        int counter = 0;
        for (String term : terms) {
            if (text.toLowerCase().contains(term.toLowerCase())) {
                counter++;
            }
        }
        return counter;
    }

    public int calculateAge(LocalDate dob, LocalDate now) {
        return Period.between(dob, now).getYears();
    }
}
