package com.roms.api.service;

import com.roms.api.model.LeaveType;
import com.roms.api.repository.LeaveTypeRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;
    public void save(LeaveType model){
        leaveTypeRepository.save(model);
    }

    public List<LeaveType> findAll(){
        return  leaveTypeRepository.findAllByOrganisation(loggedIn.getOrg());
    }
}
