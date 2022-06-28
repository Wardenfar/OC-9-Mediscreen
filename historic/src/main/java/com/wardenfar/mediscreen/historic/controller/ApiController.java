package com.wardenfar.mediscreen.historic.controller;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.model.AddHistoricModel;
import com.wardenfar.mediscreen.historic.service.HistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class ApiController {

    @Autowired
    HistoricService historicService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> add(AddHistoricModel model) {
        try {
            Historic historic = model.convertToDocument();
            String id = historicService.addHistoric(historic);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Historic> fetch(@PathVariable String id) {
        Optional<Historic> historic = historicService.findById(id);
        if (historic.isPresent()) {
            return ResponseEntity.ok(historic.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
