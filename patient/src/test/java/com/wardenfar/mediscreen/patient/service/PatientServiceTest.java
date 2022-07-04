package com.wardenfar.mediscreen.patient.service;

import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.model.PatientModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

@SpringBootTest
@Testcontainers
class PatientServiceTest {

    @Container
    static MySQLContainer mysql = new MySQLContainer("mysql");

    @Autowired
    PatientService patientService;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void insert() {
        assert this.patientService.findAll().size() == 0;

        PatientModel patient = PatientModel.builder()
                .given("John")
                .family("Doe")
                .dob(LocalDate.now())
                .address("5 rue XX")
                .phone("0102030405")
                .sex(Patient.Sex.M)
                .build();

        Long id = this.patientService.addPatient(patient);
        assert this.patientService.findAll().size() == 1;

        Patient findById = this.patientService.findById(id).get();
        assert findById.getGiven().equals("John");
        assert findById.getSex().equals(Patient.Sex.M);
    }

    @AfterEach
    void afterEach() {
        this.patientService.deleteAll();
    }
}