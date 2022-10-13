package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeAddress;
import com.roms.api.repository.EmployeeAddressRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeAddressService {

    @Autowired
    private EmployeeAddressRepository employeeAddressRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeAddress save(EmployeeAddress employeeAddress){
        employeeAddress.setCreateBy(loggedIn.getUser());
        employeeAddress.setCreateDate(Instant.now());
        employeeAddress.setOrganisation(loggedIn.getOrg());
        return employeeAddressRepository.save(employeeAddress);
    }

    public List<EmployeeAddress> findAllByEmployeId(String employeeId){
       return employeeAddressRepository.findByEmployeAndOrganisation(new Employe(employeeId), loggedIn.getOrg());
    }

    public EmployeeAddress findByAddressType(int addresstype){
       Optional<EmployeeAddress> employeeAddress =   employeeAddressRepository.findByEmployeAndOrganisationAndType(loggedIn.getUser().getEmployeId(), loggedIn.getOrg(),addresstype);
       if(employeeAddress.isPresent()){
           return employeeAddress.get();
       }else {
           return  null;
       }
    }

}
