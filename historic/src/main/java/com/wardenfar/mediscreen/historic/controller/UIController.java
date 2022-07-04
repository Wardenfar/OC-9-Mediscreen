package com.wardenfar.mediscreen.historic.controller;

import com.wardenfar.mediscreen.historic.document.Historic;
import com.wardenfar.mediscreen.historic.model.HistoricModel;
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

    @GetMapping("/view/{id}")
    public String add(@PathVariable String id, Model model) {
        Optional<Historic> historic = historicService.findById(id);
        if (historic.isEmpty()) {
            throw new NotFoundException("Historic not found");
        } else {
            model.addAttribute("historic", historic.get());
            return "view";
        }
    }

    @PostMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String update(@PathVariable String id, @Valid HistoricModel historicModel) {
        Optional<Historic> historicOptional = historicService.findById(id);
        if (historicOptional.isPresent()) {
            Historic historic = historicOptional.get();
            historic.setNotes(historicModel.getNotes());
            historicService.update(historic);
            return "redirect:/view/" + id;
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
