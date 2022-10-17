package com.roms.api.controller;

import com.roms.api.model.EmployeeSkilsLicence;
import com.roms.api.service.EmployeeLicenceService;
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
@RequestMapping(value = "/v1/employeelicence")
public class EmployeeLicenceController {

    @Autowired
    private EmployeeLicenceService employeeLicenceService;


    @PostMapping()
    public ResponseEntity<?> saveCirtificate(@RequestBody() EmployeeSkilsLicence employeeCertificate){
        Map<String,Object> response = new HashMap();
        try {
            EmployeeSkilsLicence employeeToken1 =employeeLicenceService.save(employeeCertificate);
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
        List<EmployeeSkilsLicence> requestedPage =  employeeLicenceService.searchByLicenceCode(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
