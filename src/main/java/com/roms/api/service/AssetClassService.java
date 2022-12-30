package com.roms.api.service;

import com.roms.api.model.AssetClass;
import com.roms.api.repository.AssetClassRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AssetClassService {

    @Autowired
    private AssetClassRepository assetClassRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public AssetClass save(AssetClass model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return  assetClassRepository.save(model);
    }

    public List<AssetClass> findAll(){
        return assetClassRepository.findAllByOrganisation(loggedIn.getOrg());
    }
}
