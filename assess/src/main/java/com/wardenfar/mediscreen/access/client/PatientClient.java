package com.wardenfar.mediscreen.access.client;

import com.wardenfar.mediscreen.access.model.Historic;
import com.wardenfar.mediscreen.access.model.Patient;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "patient", url = "http://patient:8081/")
public interface PatientClient {

    @RequestMapping(method = RequestMethod.GET, value = "/patient/fetch/{patientId}")
    Patient getById(@PathVariable("patientId") Integer patientId);

}
