package com.roms.api.service;

import com.roms.api.model.EmployeeAddress;
import com.roms.api.repository.EmployeeAddressRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmployeeAddressService {

    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeAddress save(EmployeeAddress employeeAddress){
        employeeAddress.setCreateBy(loggedIn.getUser());
        employeeAddress.setCreateDate(Instant.now());
        return employeeAddressRepository.save(employeeAddress);
    }

}
