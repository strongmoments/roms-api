package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import com.roms.api.repository.UserRolesMapRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRolesMapService {
    @Autowired
    private UserRolesMapRepository userRolesMapRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public void save(UserRolesMap model){
        userRolesMapRepository.save(model);

    }
    public List<UserRolesMap> findAllByUserId(String userId){
        return  userRolesMapRepository.findAllByUserId(userId);
    }

    public List<Employe> findAllEmployeeByRoleName(String role, String orgId){
        Organisation org = new Organisation();
                org.setId(orgId);
        List<Employe> employes = new ArrayList<>();
        List<UserRolesMap>  responseList =  userRolesMapRepository.findAllByRoleIdNameAndOrganisation(role, org);
        responseList.forEach(obj->{
            employes.add(obj.getUserId().getEmployeId());
        });
        return employes;
    }
}
