package com.roms.api.service;


import com.roms.api.model.RolePermission;
import com.roms.api.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;


    public void save(RolePermission model){

        rolePermissionRepository.save(model);
    }
}
