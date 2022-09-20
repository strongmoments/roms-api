package com.roms.api.controller;

import com.roms.api.service.DepartmentService;
import com.roms.api.service.EmployeService;
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
@RequestMapping(value = "/v1/managers")
public class ManagerController {

    @Autowired
    private EmployeService employeService;

    @GetMapping(value = "/load")
    public ResponseEntity<?> loadDepartments() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",employeService.findAllManagers());
        } catch (Exception e){

            response.put("status","error");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
