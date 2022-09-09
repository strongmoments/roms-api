package com.roms.api.service;

import com.roms.api.model.EmployeeResignation;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeResignationService {

    @Autowired
    private EmployeeResignationService employeeResignationService;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeResignation save(EmployeeResignation employeeResignation){

        return null;
    }

}
