package com.roms.api.service;

import com.roms.api.model.Roles;
import com.roms.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Roles> findByRoleName(String roleName){
        return roleRepository.findByRolename(roleName);
    }
}
