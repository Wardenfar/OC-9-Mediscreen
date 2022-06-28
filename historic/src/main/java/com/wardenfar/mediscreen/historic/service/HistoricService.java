package com.wardenfar.mediscreen.historic.service;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.repository.HistoricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricService {

    @Autowired
    HistoricRepository historicRepo;

    public String addHistoric(Historic historic) {
        Historic inserted = this.historicRepo.insert(historic);
        return inserted.getId();
    }

    public void update(Historic historic) {
        this.historicRepo.save(historic);
    }

    public Optional<Historic> findById(String id) {
        return this.historicRepo.findById(id);
    }

    public Optional<Historic> findByPatientId(Integer patientId) {
        return this.historicRepo.findByPatientId(patientId);
    }

    public List<Historic> findAll() {
        return this.historicRepo.findAll();
    }
}
