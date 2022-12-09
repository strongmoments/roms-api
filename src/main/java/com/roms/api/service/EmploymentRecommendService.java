package com.roms.api.service;

import com.roms.api.model.EmploymentRecommendation;
import com.roms.api.repository.EmploymentRecommendRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmploymentRecommendService {

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private EmploymentRecommendRepository employmentRecommendRepository;

    public EmploymentRecommendation save(EmploymentRecommendation model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        model.setInitiatedBy(loggedIn.getUser().getEmployeId());
        model.setRequestedDate(Instant.now());
        return employmentRecommendRepository.save(model);
    }

    public List<EmploymentRecommendation> findAll(){
        return employmentRecommendRepository.findAll();

    }
}
