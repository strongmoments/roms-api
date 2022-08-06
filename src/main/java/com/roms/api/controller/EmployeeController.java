package com.roms.api.controller;

import com.roms.api.kafka.KafkaProducer;
import com.roms.api.model.Employe;
import com.roms.api.model.Users;
import com.roms.api.service.EmployeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employe")

public class EmployeeController {

    @Autowired
    private KafkaProducer kafkaProducer;
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("rtl")
    String kafkaGroupId;

    @Value("employee-rtl.kafka.data.save")
    String postBrandTopic;

    @Autowired
    private EmployeService employeService;



    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/load", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addLayeredBrand(){
        logger.info(("Process add new brand"));
        Map<String, Object> response = new HashMap<>();
        try {
           response.put("data",employeService.findAll());
        } catch (Exception e){
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status","error");
            response.put("error",e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
