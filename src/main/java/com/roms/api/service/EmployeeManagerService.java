package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.EmployeeSkilsCirtificate;
import com.roms.api.repository.EmployeeManagerRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeManagerService {

    @Autowired
    private EmployeeManagerRepository employeeManagerRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public void save(EmployeeManagers employeeManagers){

        if(! isSameManagerExist(employeeManagers.getEmploye().getId(), employeeManagers.getManagers().getId())){
            employeeManagers.setCreateBy(loggedIn.getUser());
            employeeManagers.setCreateDate(Instant.now());
            employeeManagers.setOrganisation(loggedIn.getOrg());
            employeeManagerRepository.save(employeeManagers);
        }

    }

    public Optional<EmployeeManagers> getManager(String employeeId){
        return employeeManagerRepository.findByEmployeAndAndOrganisation(new Employe(employeeId), loggedIn.getOrg());
    }

    public boolean isSameManagerExist(String employeeId, String managerId){
        if(employeeManagerRepository.findByEmployeAndManagersAndOrganisation(new Employe(employeeId),new Employe(managerId), loggedIn.getOrg()).isEmpty()){
            return false;
        }
        return true;

    }
    public void updateManager(String employeeId, String managerId){
        Optional<EmployeeManagers> managerDetails = employeeManagerRepository.findByEmployeAndOrganisation(new Employe(employeeId), loggedIn.getOrg());
        if(!managerDetails.isEmpty()){
            EmployeeManagers employeeManagers =managerDetails.get();
            employeeManagers.setManagers(new Employe(managerId));
            employeeManagers.setUpdateBy(loggedIn.getUser());
            employeeManagers.setLastUpdateDate(Instant.now());
            employeeManagerRepository.save(employeeManagers);
        }

    }

    public List<EmployeeManagers> searchByEmployeeName(String searchText){

        return  employeeManagerRepository.findAllByManagersFirstNameContainingIgnoreCaseOrManagersLastNameContainingIgnoreCaseAndOrganisation(searchText,searchText, loggedIn.getOrg());
    }

}
