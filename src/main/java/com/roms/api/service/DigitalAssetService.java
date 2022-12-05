package com.roms.api.service;

import com.roms.api.model.DigitalAssets;
import com.roms.api.repository.DigitalAssetRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DigitalAssetService {

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private DigitalAssetRepository digitalAssetRepository;


    public DigitalAssets save(DigitalAssets digitalAssets){
        digitalAssets.setCreateBy(loggedIn.getUser());
        digitalAssets.setOrganisation(loggedIn.getOrg());
        digitalAssets.setCreateDate(Instant.now());
        return digitalAssetRepository.save(digitalAssets);

    }

    public DigitalAssets update(DigitalAssets digitalAssets){
        digitalAssets.setLastUpdateDate(Instant.now());
         return digitalAssetRepository.save(digitalAssets);
    }

    public List<DigitalAssets> findAll(){
       return  digitalAssetRepository.findAllByOrganisation(loggedIn.getOrg());
    }

}
