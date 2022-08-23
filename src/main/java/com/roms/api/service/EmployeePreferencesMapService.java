package com.roms.api.service;

import com.roms.api.model.EmployeePreferencesMap;
import com.roms.api.repository.EmployeePreferencesMapRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmployeePreferencesMapService {

    @Autowired
    private EmployeePreferencesMapRepository employeePreferencesMapRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeePreferencesMap save(EmployeePreferencesMap employeePreferencesMap){
        employeePreferencesMap.setOrganisation(loggedIn.getOrg());
        employeePreferencesMap.setCreateBy(loggedIn.getUser());
        employeePreferencesMap.setCreateDate(Instant.now());
        return employeePreferencesMapRepository.save(employeePreferencesMap);
    }
}
