package com.wardenfar.mediscreen.historic.controller;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.error.NotFoundException;
import com.wardenfar.mediscreen.historic.model.AddHistoricModel;
import com.wardenfar.mediscreen.historic.service.HistoricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patHistory")
public class ApiController {

    @Autowired
    HistoricService historicService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(AddHistoricModel model) {
        try {
            Historic historic = model.convertToDocument();
            String id = historicService.addHistoric(historic);
            return "success : " + id;
        } catch (Exception e) {
            return "fail : " + e.getMessage();
        }
    }

    @GetMapping("/fetch/{id}")
    public Historic fetch(@PathVariable String id) {
        Optional<Historic> historic = historicService.findById(id);
        if (historic.isEmpty()) {
            throw new NotFoundException("Historic not found");
        } else {
            return historic.get();
        }
    }
}
