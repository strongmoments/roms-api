package com.roms.api.service;

import com.roms.api.model.Departments;
import com.roms.api.repository.DepartmentsRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public Departments save(Departments departments){
        Optional<Departments> department = findByName(departments.getCode());
        if(!department.isEmpty()){
            departments.setOrganisation(loggedIn.getOrg());
            departments.setCreateBy(loggedIn.getUser());
            departments.setCreateDate(Instant.now());
            return  departmentsRepository.save(departments);

        }else{
            return department.get();
        }
    }

    public List<Departments> findAllDepartments(){
        return departmentsRepository.findAllByOrganisation(loggedIn.getOrg());

    }

    public Optional<Departments> findByName(String name){
        return departmentsRepository.findByCodeAndOrganisation(name,loggedIn.getOrg());

    }
}
