package com.roms.api.service;

import com.roms.api.model.EmployeeCertificate;
import com.roms.api.model.EmployeeLicence;
import com.roms.api.repository.EmployeeCertificateRepository;
import com.roms.api.repository.EmployeeLicenceRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeLicenceService {

    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeLicenceRepository employeeLicenceRepository;

    public EmployeeLicence save(EmployeeLicence model){
        model.setCode(model.getCode().toLowerCase());
        Optional<EmployeeLicence> employeeCertificate1 = employeeLicenceRepository.findByCodeAndOrganisation(model.getCode() ,loggedIn.getOrg());
        if( employeeCertificate1.isPresent()){
            return  employeeCertificate1.get();
        }else{
            model.setCreateBy(loggedIn.getUser());
            model.setCreateDate(Instant.now());
            model.setOrganisation(loggedIn.getOrg());
            return employeeLicenceRepository.save(model);
        }
    }

    public List<EmployeeLicence> searchByLicenceCode(String searchText){

        return  employeeLicenceRepository.findAllByCodeContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }
}
