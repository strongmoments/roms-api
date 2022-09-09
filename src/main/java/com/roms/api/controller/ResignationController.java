package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.EmployeeResignation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/resignation")
public class ResignationController {

    @RequestMapping(value = "/apply" , method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE  })
    public ResponseEntity<?> submitInspection(@RequestParam String inputJsonString, @RequestParam(value="files") MultipartFile file) throws IOException {

        Map<String,Object> response = new HashMap();
        try {
            ObjectMapper mapper  = new ObjectMapper();
            Map<String, MultipartFile> imageData = new HashMap();
            EmployeeResignation employeeResignation = mapper.readValue(inputJsonString, EmployeeResignation.class);

            response.put("status","success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

