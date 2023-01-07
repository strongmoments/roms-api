package com.roms.api.service;

import com.roms.api.model.Location;
import com.roms.api.repository.LocationRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public Location save(Location location){
        location.setOrganisation(loggedIn.getOrg());
        location.setCreateBy(loggedIn.getUser());
        location.setCreateDate(Instant.now());
        return locationRepository.save(location);
    }

    public List<Location> findAll(){
        return locationRepository.findAllByOrganisation(loggedIn.getOrg());
    }

}
