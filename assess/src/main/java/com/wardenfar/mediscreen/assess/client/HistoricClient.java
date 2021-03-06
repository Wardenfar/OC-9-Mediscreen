package com.wardenfar.mediscreen.assess.client;

import com.wardenfar.mediscreen.assess.model.AddNotesModel;
import com.wardenfar.mediscreen.assess.model.Historic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "historic", url = "http://historic:8082/")
public interface HistoricClient {

    @RequestMapping(method = RequestMethod.POST, value = "/patHistory/add", consumes = "application/x-www-form-urlencoded")
    String add(AddNotesModel model);

    @RequestMapping(method = RequestMethod.GET, value = "/patHistory/fetch/patient/{patientId}")
    Historic getByPatientId(@PathVariable("patientId") Long patientId);

}
