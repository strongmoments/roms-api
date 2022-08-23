package com.roms.api.service;

import com.roms.api.model.UserPreferencesMap;
import com.roms.api.repository.UserPreferencesMapRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class UserPreferencesMapService {

    @Autowired
    private UserPreferencesMapRepository userPreferencesMapRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public UserPreferencesMap save(UserPreferencesMap userPreferencesMap){
        userPreferencesMap.setOrganisation(loggedIn.getOrg());
        userPreferencesMap.setCreateBy(loggedIn.getUser());
        userPreferencesMap.setCreateDate(Instant.now());
        return userPreferencesMapRepository.save(userPreferencesMap);
    }

}
