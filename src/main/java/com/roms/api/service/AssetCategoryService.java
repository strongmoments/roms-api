package com.roms.api.service;

import com.roms.api.model.AssetCategory;
import com.roms.api.model.AssetClass;
import com.roms.api.repository.AssetCategoryRepository;
import com.roms.api.repository.AssetClassRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AssetCategoryService {
    @Autowired
    private AssetCategoryRepository assetCategoryRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public AssetCategory save(AssetCategory model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return assetCategoryRepository.save(model);
    }

    public List<AssetCategory> findAll(){
        return assetCategoryRepository.findAllByOrganisation(loggedIn.getOrg());
    }
}
