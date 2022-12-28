package com.roms.api.service;

import com.roms.api.model.CheckInCheckOutHistory;
import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import com.roms.api.repository.CheckInCheckOutHistoryRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CheckInCheckOutHistoryService {

    @Autowired
    private CheckInCheckOutHistoryRepository checkInCheckOutHistoryRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public void saveAll(List<CheckInCheckOutHistory> models){
        checkInCheckOutHistoryRepository.saveAll(models);
    }

    public Page<CheckInCheckOutHistory> findAllAttendanceByEmployeeId(String employeeId, int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("checkout").descending());

        return checkInCheckOutHistoryRepository.findAllByEmployeAndOrganisation(new Employe(employeeId),loggedIn.getOrg(),pageble);
    }

    public Page<CheckInCheckOutHistory> findAllAttendance(int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("checkout").descending());

        return checkInCheckOutHistoryRepository.findAllByOrganisation(loggedIn.getOrg(),pageble);
    }


}
