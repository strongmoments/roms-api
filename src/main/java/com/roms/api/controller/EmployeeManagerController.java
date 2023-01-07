package com.roms.api.controller;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employee/manager")
public class EmployeeManagerController {

    @Autowired
    protected EmployeService  employeService;

    @GetMapping(value = "")
    public ResponseEntity<?> searchByCirtificateCode(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<Map<String,Object>> dataList  = new ArrayList<Map<String,Object>>();
        List<Employe> requestedPage = employeService.loadAndFilterManager(searchText);
        requestedPage.forEach(obj->{
            Map<String,Object>  data = new HashMap<>();
            data.put("id",obj.getId());
            data.put("name",obj.getFirstName()+" "+obj.getLastName());
            dataList.add(data);
        });

        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }
}
