package com.roms.api.controller;

import com.roms.api.model.EmployeeSkilsLicence;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.service.EmployeeSkilsLicenceService;
import com.roms.api.service.EmployeeSkilsPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employeeskils/plant")
public class EmployeeSkilsPlantController {

    @Autowired
    private EmployeeSkilsPlantService employeeSkilsPlantService;


    @PostMapping()
    public ResponseEntity<?> saveCirtificate(@RequestBody() EmployeeSkilsPlant employeeCertificate){
        Map<String,Object> response = new HashMap();
        try {
            EmployeeSkilsPlant employeeToken1 =employeeSkilsPlantService.save(employeeCertificate);
            response.put("status","success");
            response.put("id",employeeToken1.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchByCirtificateCode(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<EmployeeSkilsPlant> requestedPage =  employeeSkilsPlantService.searchByLicenceCode(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
