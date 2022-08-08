package com.roms.api.service;


import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import com.roms.api.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeesRepository;

    public Employe save(Employe employeModel){
        return employeModel = employeesRepository.save(employeModel);

    }


    public List<Employe> findAll(){
        Map<String,Object> loggedInUserDetails =(Map<String,Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return employeesRepository.findAllByOrganisation(new Organisation(loggedInUserDetails.get("orgId").toString()));
    }


}
