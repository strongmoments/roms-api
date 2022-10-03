package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.EmployeeLicence;
import com.roms.api.requestInput.OnboardingEmergencyContactInput;
import com.roms.api.requestInput.OnboardingPersonalDetailInput;
import com.roms.api.service.EmployeeLicenceService;
import com.roms.api.service.EmployeeOnboardingService;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employee/onboard")
public class EmployeeOnboardingController {


    @Autowired
    private EmployeeOnboardingService employeeOnboardingService;

    @PostMapping(value = "/personal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody() OnboardingPersonalDetailInput personalDetail) {
        Map<String, Object> response = new HashMap();
        response.put("status","success");
      //  employeeOnboardingService.oboardPersonalDetail(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/emergency", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody() OnboardingEmergencyContactInput personalDetail) {
        Map<String, Object> response = new HashMap();
        response.put("status","success");
        //  employeeOnboardingService.oboardPersonalDetail(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/personal/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> next(@RequestBody() OnboardingPersonalDetailInput personalDetail) {
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.oboardPersonalDetail(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/emergency/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> next(@RequestBody() OnboardingEmergencyContactInput personalDetail) {
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.oboardEmergencyDetail(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping(value = "/personal/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadPersonal() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("personal");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/emergency/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> emergency() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("emergency");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/loadAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadAll() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadAll();
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
