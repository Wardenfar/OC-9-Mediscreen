package com.wardenfar.mediscreen.patient.service;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.error.NotFoundException;
import com.wardenfar.mediscreen.patient.model.PatientModel;
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

    public Long addPatient(PatientModel model) {
        Patient patient = new Patient();
        model.updateEntity(patient);
        Patient saved = this.patientRepo.save(patient);
        return saved.getId();
    }

    public void updatePatient(Long id, PatientModel model) {
        Patient patient = this.patientRepo.findById(id).orElseThrow(() -> new NotFoundException("Patient not found"));
        model.updateEntity(patient);
        this.patientRepo.save(patient);
    }

    public List<Patient> findAll() {
        return this.patientRepo.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return this.patientRepo.findById(id);
    }

    public void deleteAll() {
        this.patientRepo.deleteAll();
    }
}
