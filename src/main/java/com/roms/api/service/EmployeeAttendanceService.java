package com.roms.api.service;

import com.roms.api.model.EmployeeAttendance;
import com.roms.api.repository.EmployeeAttendanceRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeAttendanceService {

    @Autowired
    private EmployeeAttendanceRepository employeeAttendanceRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeAttendance save(EmployeeAttendance model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return employeeAttendanceRepository.save(model);
    }

    public EmployeeAttendance update(EmployeeAttendance model){
        model.setLastUpdateDate(Instant.now());
        model.setUpdateBy(loggedIn.getUser());
        return employeeAttendanceRepository.save(model);
    }

    public Optional<EmployeeAttendance> findById(String attandenceId){
        return employeeAttendanceRepository.findById(attandenceId);
    }



}
