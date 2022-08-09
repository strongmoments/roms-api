package com.roms.api.service;

import com.roms.api.model.LeaveDetails;
import com.roms.api.repository.LeaveDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveDetailsService {
@Autowired
    private LeaveDetailsRepository leaveDetailsRepository;

public void save(LeaveDetails model){
    leaveDetailsRepository.save(model);
}
}
