package com.roms.api.service;

import com.roms.api.model.*;
import com.roms.api.repository.UserRolesMapRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public Roles getEmployeeRoles(String employeeId){
        Optional<UserRolesMap>  userRolesMap = userRolesMapRepository.findByUserIdEmployeIdAndOrganisation(new Employe(employeeId),loggedIn.getOrg());
        if(userRolesMap.isPresent()){
           return userRolesMap.get().getRoleId();
        }else {
            return null;
        }
    }

    public boolean isSuperWiser(String employeeId,String orgId){
        AtomicBoolean isSuperWiser = new AtomicBoolean(false);
        List<Employe>  employeList = findAllEmployeeByRoleName("ROLE_SUPERVISOR",  orgId);
        employeList.forEach(obj->{
            if(obj.getId().equalsIgnoreCase(employeeId)){
                isSuperWiser.set(true);
            }
        });
        return isSuperWiser.get();
    }
}
