package com.wardenfar.mediscreen.patient.service;

import com.wardenfar.mediscreen.patient.PatientApplication;
import com.wardenfar.mediscreen.patient.entity.Patient;
import com.wardenfar.mediscreen.patient.error.NotFoundException;
import com.wardenfar.mediscreen.patient.model.AddPatientModel;
import com.wardenfar.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = PatientApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:integration_test.properties")
class PatientServiceTest {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void add_one_patient() throws Exception {
        assertEquals(0, patientRepository.count());

        AddPatientModel patient = AddPatientModel.builder()
                .address("50 rue de paris")
                .dob(LocalDate.of(2005, 10, 30))
                .sex(Patient.Genre.M)
                .family("Smith")
                .given("John")
                .phone("0503010506")
                .build();
        Long id = this.patientService.addPatient(patient);

        assertEquals(1, patientRepository.count());

        Patient patientInDB = this.patientRepository.findById(id).get();
        assertEquals("Smith", patientInDB.getNom());

        this.mvc.perform(get("/api/v1/patient").param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("prenom", is("John")));
    }

    @Test
    public void get_non_existent_patient() throws Exception {
        // The first id given by MYSQL is 1
        // So the patient with id=0 never exist
        this.mvc.perform(get("/api/v1/patient").param("id", String.valueOf(0)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }
}