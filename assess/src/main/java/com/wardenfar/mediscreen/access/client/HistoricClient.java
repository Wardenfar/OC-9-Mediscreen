package com.wardenfar.mediscreen.access.client;

import com.wardenfar.mediscreen.access.model.Historic;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "historic", url = "http://historic:8082/")
public interface HistoricClient {

    @RequestMapping(method = RequestMethod.GET, value = "/patHistory/fetch/patient/{patientId}")
    Historic getByPatientId(@PathVariable("patientId") Integer patientId);

}
