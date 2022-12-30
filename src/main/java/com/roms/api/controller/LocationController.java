package com.roms.api.controller;

import com.roms.api.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping(value = "")
    public ResponseEntity<?> loadAllLocation() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",locationService.findAll());
        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
