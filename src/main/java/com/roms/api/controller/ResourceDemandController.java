package com.roms.api.controller;

import com.roms.api.model.EmployeeCertificate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/jobs/resource/demand")
public class ResourceDemandController {

    @PostMapping()
    public ResponseEntity<?> saveJobResourceDemand(){
        Map<String,Object> response = new HashMap();
        try {

            response.put("status","success");
         //   response.put("id",employeeCertificate1.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
