package com.roms.api.service;

import com.roms.api.model.LeaveType;
import com.roms.api.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public void save(LeaveType model){
        leaveTypeRepository.save(model);
    }
}
