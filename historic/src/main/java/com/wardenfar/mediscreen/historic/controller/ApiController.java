package com.wardenfar.mediscreen.historic.controller;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.model.AddNotesModel;
import com.wardenfar.mediscreen.historic.service.HistoricService;
import io.swagger.annotations.*;
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

    @ApiOperation("Ajoute des notes à l'historique d'un patient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "L'ajout a réussi"),
            @ApiResponse(code = 400, message = "L'ajout n'a pas abouti")
    })
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> addNotes(@ApiParam(name = "Historique du patient") AddNotesModel model) {
        try {
            String id = historicService.addOrCreateNotes(model.getPatId(), model.getE());
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation("Récupère l'historique d'un patient avec l'ID de l'historique")
    @ApiResponses({
            @ApiResponse(code = 200, message = "L'historique a été trouvé"),
            @ApiResponse(code = 404, message = "L'historique n'a pas été trouvé")
    })
    @GetMapping("/fetch/{id}")
    public ResponseEntity<Historic> fetch(@ApiParam(name = "ID de l'historique") @PathVariable String id) {
        Optional<Historic> historic = historicService.findById(id);
        if (historic.isPresent()) {
            return ResponseEntity.ok(historic.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("Récupère l'historique d'un patient avec l'ID du patient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "L'historique a été trouvé"),
            @ApiResponse(code = 404, message = "L'historique n'a pas été trouvé")
    })
    @GetMapping("/fetch/patient/{id}")
    public ResponseEntity<Historic> fetchByPatient(@ApiParam(name = "ID du patient") @PathVariable Integer id) {
        Optional<Historic> historic = historicService.findByPatientId(id);
        if (historic.isPresent()) {
            return ResponseEntity.ok(historic.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
