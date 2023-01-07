package com.roms.api.service;

import com.roms.api.model.EmployeeSkilsLicence;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.repository.EmployeeSkilsLicenceRepository;
import com.roms.api.repository.EmployeeSkilsPlantRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSkilsPlantService {

    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeSkilsPlantRepository employeeSkilsPlantRepository;

    public EmployeeSkilsPlant save(EmployeeSkilsPlant model){
        model.setCode(model.getCode().toLowerCase());
        Optional<EmployeeSkilsPlant> employeeCertificate1 = employeeSkilsPlantRepository.findByCodeAndOrganisation(model.getCode() ,loggedIn.getOrg());
        if( employeeCertificate1.isPresent()){
            return  employeeCertificate1.get();
        }else{
            model.setCreateBy(loggedIn.getUser());
            model.setCreateDate(Instant.now());
            model.setOrganisation(loggedIn.getOrg());
            return employeeSkilsPlantRepository.save(model);
        }
    }

    public List<EmployeeSkilsPlant> searchByLicenceCode(String searchText){

        return  employeeSkilsPlantRepository.findAllByCodeContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }
}
