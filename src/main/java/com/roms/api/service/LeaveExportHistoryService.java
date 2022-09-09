package com.roms.api.service;


import com.roms.api.model.LeaveExportHistory;
import com.roms.api.model.LeaveRequest;
import com.roms.api.repository.LeaveExportHistoryRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LeaveExportHistoryService {

    @Autowired
    private LeaveExportHistoryRepository leaveExportHistoryRepository;


    @Autowired
    private LoggedInUserDetails loggedIn;

    public LeaveExportHistory save(LeaveExportHistory leaveExportHistory){
        leaveExportHistory.setExportBy(loggedIn.getUser().getEmployeId());
        leaveExportHistory.setCreateBy(loggedIn.getUser());
        leaveExportHistory.setCreateDate(Instant.now());
        leaveExportHistory.setOrganisation(loggedIn.getOrg());
        return leaveExportHistoryRepository.save(leaveExportHistory);
    }
    public Page<LeaveExportHistory> findAll(int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("exportDate").descending());
        return leaveExportHistoryRepository.findAllByOrganisation(loggedIn.getOrg(),pageble);

    }


}
