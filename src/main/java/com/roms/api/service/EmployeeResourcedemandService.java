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
import java.util.Optional;

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

    public EmployeeResourcedemand update(EmployeeResourcedemand employeeResourcedemand){
        employeeResourcedemand.setUpdateBy(loggedIn.getUser());
        employeeResourcedemand.setLastUpdateDate(Instant.now());
        return employeeResourcedemandRepository.save(employeeResourcedemand);
    }

    public List<EmployeeResourcedemand> findAll(){
        return employeeResourcedemandRepository.findAllByOrganisationOrderByCreateDateDesc(loggedIn.getOrg());
    }
    public List<EmployeeResourcedemand> findAllPendingDemand(){
        return employeeResourcedemandRepository.findAllByOrganisationAndStatusOrderByCreateDateDesc(loggedIn.getOrg(),0);
    }

    public List<EmployeeResourcedemand> findAllInternalPendingDemand(){
        return employeeResourcedemandRepository.findAllByOrganisationAndStatusAndDemandTypeOrderByCreateDateDesc(loggedIn.getOrg(),0,1);
    }
    
    public Optional<EmployeeResourcedemand> findById(String id){
        return employeeResourcedemandRepository.findById(id);
    }


    
    public List<EmployeeResourcedemand> findbyEmployeeId(String employeeId){
        return employeeResourcedemandRepository.findAllByOrganisationAndHiringManagerOrCreateByEmployeIdAndStatusOrderByCreateDateDesc(loggedIn.getOrg(), new Employe(employeeId),new Employe(employeeId),0);
    }

    public List<EmployeeResourcedemand> findPendingInternalDemandByEmployeeId(String employeeId){
        // demand type  1 if for internal
        return employeeResourcedemandRepository.findAllByDemandTypeAndOrganisationAndHiringManagerOrCreateByEmployeIdAndStatusOrderByCreateDateDesc(0,loggedIn.getOrg(), new Employe(employeeId),new Employe(employeeId),0);
    }


}
