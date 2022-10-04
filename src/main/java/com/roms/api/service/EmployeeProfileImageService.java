package com.roms.api.service;

import com.roms.api.model.EmployeeProfileImage;
import com.roms.api.repository.EmployeeProfileImageRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmployeeProfileImageService {

    @Autowired
    private EmployeeProfileImageRepository employeeProfileImageRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeProfileImage save(EmployeeProfileImage employeeProfileImage){
        employeeProfileImage.setCreateBy(loggedIn.getUser());
        employeeProfileImage.setCreateDate(Instant.now());
        return employeeProfileImageRepository.save(employeeProfileImage);

    }
}
