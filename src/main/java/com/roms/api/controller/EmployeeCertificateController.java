package com.roms.api.controller;

import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.model.EmployeeCertificate;
import com.roms.api.service.EmployeeCertificateService;
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
@RequestMapping(value = "/v1/employeecertificates")
public class EmployeeCertificateController {

    @Autowired
    private EmployeeCertificateService employeeCertificateService;
    @PostMapping()
    public   ResponseEntity<?> saveCirtificate(@RequestBody() EmployeeCertificate employeeCertificate){
        Map<String,Object> response = new HashMap();
        try {
            EmployeeCertificate employeeCertificate1 =employeeCertificateService.save(employeeCertificate);
            response.put("status","success");
            response.put("id",employeeCertificate1.getId());

           return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
        response.put("error", e.getMessage());
        response.put("status", "error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchByCirtificateCode(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<EmployeeCertificate> requestedPage =  employeeCertificateService.searchByCirtificateCode(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
