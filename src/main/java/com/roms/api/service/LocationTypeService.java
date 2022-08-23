package com.roms.api.service;

import com.roms.api.model.LocationType;
import com.roms.api.repository.LocationTypeRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LocationTypeService {

    @Autowired
    private LocationTypeRepository locationTypeRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public LocationType save(LocationType locationType){
        locationType.setOrganisation(loggedIn.getOrg());
        locationType.setCreateBy(loggedIn.getUser());
        locationType.setCreateDate(Instant.now());
        return locationTypeRepository.save(locationType);
    }
}
