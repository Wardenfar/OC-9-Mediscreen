package com.wardenfar.mediscreen.historic.service;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.repository.HistoricRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class HistoricServiceTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo");

    @Autowired
    HistoricService historicService;

    @Autowired
    HistoricRepository historicRepo;

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", () -> mongo.getHost());
        registry.add("spring.data.mongodb.port", () -> mongo.getMappedPort(27017));
    }

    @Test
    void insert() {
        assert this.historicService.findAll().size() == 0;
        Historic historic = new Historic();
        historic.setContent("content");
        historic.setPatientId(50);
        String id = this.historicService.addHistoric(historic);
        assert this.historicService.findAll().size() == 1;

        Historic findById = this.historicService.findById(id).get();
        assert findById.getContent().equals("content");
        assert findById.getPatientId() == 50;

        Historic findByPatientId = this.historicService.findByPatientId(50).get();
        assert findByPatientId.getId().equals(id);
        assert findByPatientId.getContent().equals("content");
    }

    @AfterEach
    void afterEach() {
        this.historicRepo.deleteAll();
    }
}