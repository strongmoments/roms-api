package com.roms.api.service;

import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.EmployeeCertificate;
import com.roms.api.repository.EmployeeCertificateRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeCertificateService {

    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeCertificateRepository employeeCertificateRepository;

    public EmployeeCertificate save(EmployeeCertificate employeeCertificate){
        employeeCertificate.setCode(employeeCertificate.getCode().toLowerCase());
        Optional<EmployeeCertificate> employeeCertificate1 = employeeCertificateRepository.findByCodeAndOrganisation(employeeCertificate.getCode() ,loggedIn.getOrg());
       if( employeeCertificate1.isPresent()){
            return  employeeCertificate1.get();
        }else{
           employeeCertificate.setCreateBy(loggedIn.getUser());
           employeeCertificate.setCreateDate(Instant.now());
           employeeCertificate.setOrganisation(loggedIn.getOrg());
          return employeeCertificateRepository.save(employeeCertificate);
       }
    }

    public List<EmployeeCertificate> searchByCirtificateCode(String searchText){

        return  employeeCertificateRepository.findAllByCodeContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }
}
