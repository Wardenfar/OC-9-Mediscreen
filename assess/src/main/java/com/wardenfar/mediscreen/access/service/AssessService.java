package com.wardenfar.mediscreen.access.service;

import com.wardenfar.mediscreen.access.client.HistoricClient;
import com.wardenfar.mediscreen.access.client.PatientClient;
import com.wardenfar.mediscreen.access.model.AssessLevel;
import com.wardenfar.mediscreen.access.model.AssessResult;
import com.wardenfar.mediscreen.access.model.Historic;
import com.wardenfar.mediscreen.access.model.Patient;
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
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Anormal",
            "Cholestérol",
            "Vertige"
    };

    public AssessResult assessPatient(Integer patientId) {
        Patient patient = patientClient.getById(patientId);
        Historic historic = historicClient.getByPatientId(patientId);

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

    private static AssessLevel calculateLevel(int termsCount, int age, boolean isMale) {
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
            if (text.contains(term)) {
                counter++;
            }
        }
        return counter;
    }

    public int calculateAge(LocalDate dob, LocalDate now) {
        return Period.between(dob, now).getYears();
    }
}
