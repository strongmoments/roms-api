package com.roms.api.service;

import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import com.roms.api.repository.UserRolesMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRolesMapService {
    @Autowired
    private UserRolesMapRepository userRolesMapRepository;

    public void save(UserRolesMap model){
        userRolesMapRepository.save(model);

    }
    public List<UserRolesMap> findAllByUserId(String userId){
        return  userRolesMapRepository.findAllByUserId(userId);
    }
}
