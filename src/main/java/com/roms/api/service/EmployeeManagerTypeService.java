package com.roms.api.service;

import com.roms.api.model.EmployeeManagerType;
import com.roms.api.repository.EmployeeManagerTypeRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeManagerTypeService {

    @Autowired
    private EmployeeManagerTypeRepository employeeManagerTypeRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeManagerType save(EmployeeManagerType employeeManagerType){
       Optional<EmployeeManagerType>  manageType = employeeManagerTypeRepository.findByCodeAndOrganisation(employeeManagerType.getCode(),loggedIn.getOrg());
       if(manageType.isEmpty()){
           employeeManagerType.setOrganisation(loggedIn.getOrg());
           employeeManagerType.setCreateBy(loggedIn.getUser());
           employeeManagerType.setCreateDate(Instant.now());
         return  employeeManagerTypeRepository.save(employeeManagerType);
       }else{
           return manageType.get();
       }
    }

}
