package com.roms.api.service;

import com.roms.api.model.ClientProject;
import com.roms.api.model.ClientProjectRole;
import com.roms.api.repository.ClientProjectRepository;
import com.roms.api.repository.ClientProjectRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientProjectService {
    @Autowired
    private ClientProjectRepository clientProjectRepository;

    public ClientProject save(ClientProject model){
        return clientProjectRepository.save(model);
    }
}
