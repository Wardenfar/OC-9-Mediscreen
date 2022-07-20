package com.wardenfar.mediscreen.assess.service;

import com.wardenfar.mediscreen.assess.model.AssessLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssessServiceTest {

    @Autowired
    AssessService assessService;

    @Test
    void calculateLevel() {
        assertEquals(AssessLevel.None, assessService.calculateLevel(0, 25, true));
        assertEquals(AssessLevel.Borderline, assessService.calculateLevel(2, 31, false));
        assertEquals(AssessLevel.InDanger, assessService.calculateLevel(3, 29, true));
        assertEquals(AssessLevel.EarlyOnset, assessService.calculateLevel(9, 50, false));
    }

    @Test
    void countTerms() {
        String[] terms = {
            "abc",
            "test"
        };
        assertEquals(1, assessService.countTerms("abc def", terms));
        assertEquals(1, assessService.countTerms("ABC def", terms));
        assertEquals(1, assessService.countTerms("TesT def", terms));
        assertEquals(1, assessService.countTerms("abc abc", terms));
        assertEquals(2, assessService.countTerms("abc test hjk", terms));
        assertEquals(2, assessService.countTerms("test abc test abc", terms));
    }

    @Test
    void calculateAge() {
        assertEquals(15, assessService.calculateAge(LocalDate.of(1000, 1,1), LocalDate.of(1015, 1,1)));
        assertEquals(14, assessService.calculateAge(LocalDate.of(1000, 1,5), LocalDate.of(1015, 1,4)));
        assertEquals(15, assessService.calculateAge(LocalDate.of(1000, 1,5), LocalDate.of(1015, 1,6)));
    }
}