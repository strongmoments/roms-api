package com.roms.api.service;

import com.roms.api.model.EmployeType;
import com.roms.api.repository.EmployeTypeRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeTypeService {
    @Autowired
    private EmployeTypeRepository employeTypeRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeType save(EmployeType model){
        Optional<EmployeType> employeType = findAllEmployeeType(model.getName());
        if(!employeType.isEmpty()){
            return employeType.get();

        }else{
            model.setOrganisation(loggedIn.getOrg());
            model.setCreateBy(loggedIn.getUser());
            model.setCreateDate(Instant.now());
            return employeTypeRepository.save(model);

        }
    }

    public List<EmployeType> findAllEmployeeType(){
        return  employeTypeRepository.findAllByOrganisation(loggedIn.getOrg());
    }

    public Optional<EmployeType> findAllEmployeeType(String name){
        return  employeTypeRepository.findByNameAndOrganisation(name,loggedIn.getOrg());
    }

}
