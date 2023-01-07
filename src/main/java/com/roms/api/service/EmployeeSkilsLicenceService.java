package com.roms.api.service;

import com.roms.api.model.EmployeeSkilsLicence;
import com.roms.api.repository.EmployeeSkilsLicenceRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSkilsLicenceService {

    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeSkilsLicenceRepository employeeLicenceRepository;

    public EmployeeSkilsLicence save(EmployeeSkilsLicence model){
        model.setCode(model.getCode().toLowerCase());
        Optional<EmployeeSkilsLicence> employeeCertificate1 = employeeLicenceRepository.findByCodeAndOrganisation(model.getCode() ,loggedIn.getOrg());
        if( employeeCertificate1.isPresent()){
            return  employeeCertificate1.get();
        }else{
            model.setCreateBy(loggedIn.getUser());
            model.setCreateDate(Instant.now());
            model.setOrganisation(loggedIn.getOrg());
            return employeeLicenceRepository.save(model);
        }
    }

    public List<EmployeeSkilsLicence> searchByLicenceCode(String searchText){

        return  employeeLicenceRepository.findAllByCodeContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }
}
