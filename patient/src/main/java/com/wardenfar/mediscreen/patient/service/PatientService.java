package com.wardenfar.mediscreen.patient.service;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.model.AddPatientModel;
import com.wardenfar.mediscreen.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    final PatientRepository patientRepo;

    public PatientService(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    public Long addPatient(AddPatientModel model) {
        Patient patient = new Patient();
        patient.setNom(model.getFamily());
        patient.setPrenom(model.getGiven());
        patient.setAdresse(model.getAddress());
        patient.setGenre(model.getSex());
        patient.setDdn(model.getDob());
        patient.setPhone(model.getPhone());

        Patient saved = this.patientRepo.save(patient);
        return saved.getId();
    }

    public List<Patient> findAll() {
        return this.patientRepo.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return this.patientRepo.findById(id);
    }
}
