package com.roms.api.service;

import com.roms.api.model.ClientProjectRole;
import com.roms.api.repository.ClientProjectRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientProjectService {
    @Autowired
    private ClientProjectRoleRepository clientProjectRoleRepository;
    public void save(ClientProjectRole model){
        clientProjectRoleRepository.save(model);
    }
}
