package com.roms.api.service;

import com.roms.api.model.LeaveAttachments;
import com.roms.api.repository.LeaveAttachmentRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LeaveAttachmentService {

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private LeaveAttachmentRepository leaveAttachmentRepository;

    public LeaveAttachments save(LeaveAttachments leaveAttachments){
        leaveAttachments.setCreateBy(loggedIn.getUser());
        leaveAttachments.setCreateDate(Instant.now());
        leaveAttachments.setOrganisation(loggedIn.getOrg());
        return  leaveAttachmentRepository.save(leaveAttachments);
    }


}


