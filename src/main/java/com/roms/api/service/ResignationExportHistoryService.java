package com.roms.api.service;

import com.roms.api.model.LeaveExportHistory;
import com.roms.api.model.ResignationExportHistory;
import com.roms.api.repository.LeaveExportHistoryRepository;
import com.roms.api.repository.ResignationExportHistoryRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ResignationExportHistoryService {

    @Autowired
    private ResignationExportHistoryRepository resignationExportHistoryRepository;


    @Autowired
    private LoggedInUserDetails loggedIn;

    public ResignationExportHistory save(ResignationExportHistory leaveExportHistory){
        leaveExportHistory.setExportBy(loggedIn.getUser().getEmployeId());
        leaveExportHistory.setCreateBy(loggedIn.getUser());
        leaveExportHistory.setCreateDate(Instant.now());
        leaveExportHistory.setOrganisation(loggedIn.getOrg());
        leaveExportHistory.setExportDate(Instant.now());
        return resignationExportHistoryRepository.save(leaveExportHistory);
    }
    public Page<ResignationExportHistory> findAll(int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("exportDate").descending());
        return resignationExportHistoryRepository.findAllByOrganisation(loggedIn.getOrg(),pageble);

    }

}
