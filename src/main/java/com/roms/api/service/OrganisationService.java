package com.roms.api.service;

import com.roms.api.model.Organisation;
import com.roms.api.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {
    @Autowired
    private com.roms.api.repository.OrganisationRepository OrganisationRepository;


    public void save(Organisation model){
        OrganisationRepository.save(model);
    }
}
