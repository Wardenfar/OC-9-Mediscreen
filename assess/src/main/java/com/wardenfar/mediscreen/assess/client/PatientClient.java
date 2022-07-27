package com.wardenfar.mediscreen.assess.client;

import com.wardenfar.mediscreen.assess.model.Patient;
import com.wardenfar.mediscreen.assess.model.PatientModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "patient", url = "http://patient:8081/")
public interface PatientClient {

    @RequestMapping(method = RequestMethod.POST, value = "/patient/add", consumes = "application/x-www-form-urlencoded")
    Integer add(PatientModel model);

    @RequestMapping(method = RequestMethod.GET, value = "/patient/fetch/{patientId}")
    Patient getById(@PathVariable("patientId") Integer patientId);

    @RequestMapping(method = RequestMethod.GET, value = "/patient/fetchByFamily/{patientFamilyName}")
    Patient getByFamilyName(@PathVariable("patientFamilyName") String patientFamilyName);

}
