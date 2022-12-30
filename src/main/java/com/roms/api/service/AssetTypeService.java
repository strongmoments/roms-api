package com.roms.api.service;

import com.roms.api.model.AssetClass;
import com.roms.api.model.AssetType;
import com.roms.api.repository.AssetClassRepository;
import com.roms.api.repository.AssetTypeRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public class AssetTypeService {

    @Autowired
    private AssetTypeRepository assetTypeRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public AssetType save(AssetType model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return assetTypeRepository.save(model);
    }

    public List<AssetType> findAll(){
        return assetTypeRepository.findAllByOrganisation(loggedIn.getOrg());
    }
}
