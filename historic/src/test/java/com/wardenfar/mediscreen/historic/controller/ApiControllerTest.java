package com.wardenfar.mediscreen.historic.controller;

import com.wardenfar.mediscreen.historic.repository.HistoricRepository;
import com.wardenfar.mediscreen.historic.service.HistoricService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ApiControllerTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo");

    @Autowired
    HistoricRepository historicRepo;

    @Autowired
    HistoricService historicService;

    @Autowired
    private MockMvc mvc;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", () -> mongo.getHost());
        registry.add("spring.data.mongodb.port", () -> mongo.getMappedPort(27017));
    }

    @Test
    void add() throws Exception {
        ResultActions actions =
                mvc.perform(post("/patHistory/add").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("patId" +
                        "=1&e" +
                        "=Patient: TestNone Practitioner's notes/recommendations: Patient states that they are " +
                        "'feeling " +
                        "terrific' Weight at or below recommended level"));
        actions.andExpect(status().is(200));
    }

    @Test
    void fetch() throws Exception {
        String id = historicService.addNotes(10, "contenu");

        mvc.perform(get("/patHistory/fetch/" + id)).andExpect(status().is(200)).andExpect(jsonPath("$.patientId",
                is(10)));

        mvc.perform(get("/patHistory/fetch/false-id")).andExpect(status().isNotFound());
    }

    @AfterEach
    void afterEach() {
        this.historicRepo.deleteAll();
    }
}