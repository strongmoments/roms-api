package com.roms.api.service;


import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import com.roms.api.repository.EmployeRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeesRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public Optional<Employe> findById(String employeeId){
        return employeesRepository.findById(employeeId);

    }
    public Employe save(Employe employeModel){
        return employeModel = employeesRepository.save(employeModel);

    }

    public List<Employe> findAllManagers(){
        return employeesRepository.findAllByManagerFlagAndOrganisation(true,loggedIn.getOrg());

    }

    public Employe update(Employe employeModel){
        employeModel.setUpdateBy(loggedIn.getUser());
        employeModel.setLastUpdateDate(Instant.now());
        return employeModel = employeesRepository.save(employeModel);

    }

    public Optional<Employe> findByEmployeeId(String employeeId){
        return  employeesRepository.findById(employeeId);
    }

    public Page<Employe> findAll(int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("lastName","firstName").ascending());
        Map<String,Object> loggedInUserDetails =(Map<String,Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return employeesRepository.findAllByOrganisation(new Organisation(loggedInUserDetails.get("orgId").toString()),pageble);
    }

    public Long getTotalEmployeeCount(){
       return employeesRepository.countAllByOrganisation(loggedIn.getOrg());
    }

    public List<Instant> findDobOfEmployees(){
        return employeesRepository.findBirthDateByOrganisation(loggedIn.getOrg().getId());
    }


}
