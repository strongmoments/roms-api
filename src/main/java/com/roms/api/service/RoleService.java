package com.roms.api.service;

import com.roms.api.model.Organisation;
import com.roms.api.model.Roles;
import com.roms.api.repository.RoleRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public Optional<Roles> findById(String roleId){
        return roleRepository.findById(roleId);
    }

    public Roles save(Roles role){
        Optional<Roles> roles  = findByRoleName(role.getName());
        if(!roles.isEmpty()){
            return roles.get();
        }else{
            role.setCreateBy(loggedIn.getUser());
            role.setOrganisation(loggedIn.getOrg());
            role.setCreateDate(Instant.now());
            return roleRepository.save(role);

        }
    }
    public Optional<Roles> findByRoleName(String roleName){
        return roleRepository.findByNameAndOrganisation(roleName,loggedIn.getOrg());
    }

    public List<Roles> findAllRoles(){
        return  roleRepository.findAllByOrganisation(loggedIn.getOrg());
    }

}
