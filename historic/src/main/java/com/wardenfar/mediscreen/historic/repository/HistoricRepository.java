package com.wardenfar.mediscreen.historic.repository;

import com.wardenfar.mediscreen.historic.document.Historic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoricRepository extends MongoRepository<Historic, String> {

    Optional<Historic> findByPatientId(Integer patientId);

    long count();

}
