package com.roms.api.service;

import com.roms.api.model.InspectionItems;
import com.roms.api.repository.InspectionItemRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class InspectionItemService {

    @Autowired
    private InspectionItemRepository inspectionItemRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public InspectionItems save(InspectionItems model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return inspectionItemRepository.save(model);
    }

    public List<InspectionItems> findAll(){
        return  inspectionItemRepository.findAllByOrganisation(loggedIn.getOrg());
    }
}
