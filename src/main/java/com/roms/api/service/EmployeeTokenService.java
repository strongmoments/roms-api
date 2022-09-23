package com.roms.api.service;

import com.roms.api.model.EmployeeLicence;
import com.roms.api.model.EmployeeToken;
import com.roms.api.repository.EmployeeLicenceRepository;
import com.roms.api.repository.EmployeeTokenRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeTokenService {

    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeTokenRepository employeeTokenRepository;

    public EmployeeToken save(EmployeeToken model){
        model.setCode(model.getCode().toLowerCase());
        Optional<EmployeeToken> employeeCertificate1 = employeeTokenRepository.findByCodeAndOrganisation(model.getCode() ,loggedIn.getOrg());
        if( employeeCertificate1.isPresent()){
            return  employeeCertificate1.get();
        }else{
            model.setCreateBy(loggedIn.getUser());
            model.setCreateDate(Instant.now());
            model.setOrganisation(loggedIn.getOrg());
            return employeeTokenRepository.save(model);
        }
    }

    public List<EmployeeToken> searchByTokenCode(String searchText){

        return  employeeTokenRepository.findAllByCodeContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }
}
