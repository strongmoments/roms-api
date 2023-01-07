package com.roms.api.controller;

import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.EmployeeSkilsCirtificate;
import com.roms.api.service.DepartmentService;
import com.roms.api.service.EmployeService;
import com.roms.api.service.EmployeeManagerService;
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
@RequestMapping(value = "/v1/employee/managers")
public class ManagerController {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeeManagerService employeeManagerService;

    @GetMapping(value = "")
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

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchByCirtificateCode(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<EmployeeManagers> requestedPage =  employeeManagerService.searchByEmployeeName(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }

}
