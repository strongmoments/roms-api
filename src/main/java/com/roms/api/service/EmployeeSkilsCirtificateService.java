package com.roms.api.service;

import com.roms.api.model.EmployeeSkilsCirtificate;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.repository.EmployeeSkilsCirtificateRepository;
import com.roms.api.repository.EmployeeSkilsPlantRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeSkilsCirtificateService {


    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeSkilsCirtificateRepository employeeSkilsCirtificateRepository;

    public EmployeeSkilsCirtificate save(EmployeeSkilsCirtificate model){
        model.setCode(model.getCode().toLowerCase());
        Optional<EmployeeSkilsCirtificate> employeeCertificate1 = employeeSkilsCirtificateRepository.findByCodeAndOrganisation(model.getCode() ,loggedIn.getOrg());
        if( employeeCertificate1.isPresent()){
            return  employeeCertificate1.get();
        }else{
            model.setCreateBy(loggedIn.getUser());
            model.setCreateDate(Instant.now());
            model.setOrganisation(loggedIn.getOrg());
            return employeeSkilsCirtificateRepository.save(model);
        }
    }

    public List<EmployeeSkilsCirtificate> searchByLicenceCode(String searchText){

        return  employeeSkilsCirtificateRepository.findAllByCodeContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }
}
