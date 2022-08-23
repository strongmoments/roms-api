package com.roms.api.service;

import com.roms.api.model.Departments;
import com.roms.api.repository.DepartmentsRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public Departments save(Departments departments){
        departments.setOrganisation(loggedIn.getOrg());
        departments.setCreateBy(loggedIn.getUser());
        departments.setCreateDate(Instant.now());
        return  departmentsRepository.save(departments);
    }

}
