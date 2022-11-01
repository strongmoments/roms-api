package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeResourcedemand;
import com.roms.api.repository.EmployeeResourcedemandRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmployeeResourcedemandService  {

    @Autowired
    private EmployeeResourcedemandRepository employeeResourcedemandRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;


    public EmployeeResourcedemand save(EmployeeResourcedemand employeeResourcedemand){
        employeeResourcedemand.setCreateBy(loggedIn.getUser());
        employeeResourcedemand.setOrganisation(loggedIn.getOrg());
        employeeResourcedemand.setCreateDate(Instant.now());
        return employeeResourcedemandRepository.save(employeeResourcedemand);
    }

    public List<EmployeeResourcedemand> findAll(){
        return employeeResourcedemandRepository.findAllByOrganisation(loggedIn.getOrg());
    }
    public List<EmployeeResourcedemand> findbyEmployeeId(String employeeId){
        return employeeResourcedemandRepository.findAllByOrganisationAndHiringManagerOrCreateByEmployeId(loggedIn.getOrg(), new Employe(employeeId),new Employe(employeeId));
    }


}
