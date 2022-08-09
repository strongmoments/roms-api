package com.roms.api.service;

import com.roms.api.model.LeaveRequest;
import com.roms.api.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveRequestService {
@Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public void LeaveRequestRepository(LeaveRequest model) {
        leaveRequestRepository.save(model);
    }
}
