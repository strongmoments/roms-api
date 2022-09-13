package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.EmployeeResignation;
import com.roms.api.repository.EmployeeResignationRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeResignationService {

    @Autowired
    private EmployeeResignationRepository employeeResignationRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private EmployeeManagerService employeeManagerService;

    public Optional<EmployeeResignation> findById(String id){
      return employeeResignationRepository.findById(id);
    }

    public EmployeeResignation resigne(EmployeeResignation employeeResignation){

        Optional<EmployeeManagers> employeeManagers = employeeManagerService.getManager(loggedIn.getUser().getEmployeId().getId());
        if(employeeManagers.isEmpty()){
            //@todo throw exception you dont have manager
            return null;
        }else{
          Employe manager =  employeeManagers.get().getManagers();
            employeeResignation.setApprover(manager);
            employeeResignation.setApplyDate(Instant.now());
            employeeResignation.setStatus(1);
            employeeResignation.setCreateBy(loggedIn.getUser());
            employeeResignation.setOrganisation(loggedIn.getOrg());
            employeeResignation.setCreateDate(Instant.now());
            employeeResignation =employeeResignationRepository.save(employeeResignation);

        }
        return employeeResignation;
    }

    public EmployeeResignation approveResignation(EmployeeResignation employeeResignation){
        Optional<EmployeeResignation> employeeResignation1 = findById(employeeResignation.getId());
        if(employeeResignation1.isEmpty()){
            //@todo throw exception you dont have manager
            return null;
        }else{
            employeeResignation =  employeeResignation1.get();
            employeeResignation.setStatus(2);
            employeeResignation.setUpdateBy(loggedIn.getUser());
            employeeResignation.setLastUpdateDate(Instant.now());
            employeeResignation =employeeResignationRepository.save(employeeResignation);
        }
        return employeeResignation;
    }

    public EmployeeResignation resectResignation(EmployeeResignation employeeResignation){
        Optional<EmployeeResignation> employeeResignation1 = findById(employeeResignation.getId());
        if(employeeResignation1.isEmpty()){
            //@todo throw exception you dont have manager
            return null;
        }else{
            employeeResignation =  employeeResignation1.get();
            employeeResignation.setStatus(3);
            employeeResignation.setUpdateBy(loggedIn.getUser());
            employeeResignation.setLastUpdateDate(Instant.now());
            employeeResignation =employeeResignationRepository.save(employeeResignation);
        }
        return employeeResignation;
    }

}
