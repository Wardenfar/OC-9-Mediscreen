package com.wardenfar.mediscreen.assess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wardenfar.mediscreen.assess.model.AssessResult;
import com.wardenfar.mediscreen.assess.service.AssessService;

@Controller
public class UIController {

    @Autowired
    public AssessService assessService;

    @GetMapping("/view/{patientId}")
    public String viewById(@PathVariable Integer patientId, Model model) {
        AssessResult result = this.assessService.assessPatientById(patientId);
        model.addAttribute("result", result.toString());
        model.addAttribute("id", patientId);
        return "view";
    }   
}
