package com.wardenfar.mediscreen.historic.controller;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.model.HistoricAddModel;
import com.wardenfar.mediscreen.historic.model.HistoricUpdateModel;
import com.wardenfar.mediscreen.historic.service.HistoricService;
import com.wardenfar.mediscreen.historic.error.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UIController {

    final HistoricService historicService;

    public UIController(HistoricService historicService) {
        this.historicService = historicService;
    }

    @GetMapping("/view/{patientId}")
    public String view(@PathVariable Integer patientId, Model model) {
        Optional<Historic> historic = historicService.findByPatientId(patientId);
        if (historic.isEmpty()) {
            return "redirect:/add";
        } else {
            model.addAttribute("historic", historic.get());
            return "view";
        }
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add(@Valid HistoricAddModel historicModel) {
        Optional<Historic> historicOptional = historicService.findByPatientId(historicModel.getPatientId());
        if (historicOptional.isEmpty()) {
            Historic historic = new Historic();
            historic.setPatientId(historicModel.getPatientId());
            historic.setNotes(historicModel.getNotes());
            historicService.update(historic);
            return "redirect:/view/" + historicModel.getPatientId();
        } else {
            return "redirect:/view/" + historicOptional.get().getPatientId();
        }
    }

    @PostMapping(value = "/update/{patientId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(@PathVariable Integer patientId, @Valid HistoricUpdateModel historicModel) {
        Optional<Historic> historicOptional = historicService.findByPatientId(patientId);
        if (historicOptional.isPresent()) {
            Historic historic = historicOptional.get();
            historic.setNotes(historicModel.getNotes());
            historicService.update(historic);
            return "redirect:/view/" + patientId;
        } else {
            return "redirect:/list";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Historic> historics = historicService.findAll();
        model.addAttribute("historics", historics);
        return "list";
    }
}
