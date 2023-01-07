package com.roms.api.service;

import com.roms.api.model.AssetClass;
import com.roms.api.model.AssetType;
import com.roms.api.model.InspectionLists;
import com.roms.api.repository.InspectionListRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class InspectionListService {

    @Autowired
    private InspectionListRepository inspectionListRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public InspectionLists save(InspectionLists model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return inspectionListRepository.save(model);
    }

    public List<InspectionLists> findAll(){
        return inspectionListRepository.findAllByOrganisation(loggedIn.getOrg());
    }

    public Optional<InspectionLists> findInspectionItem(String make, String model, String assetClassId, String assetTypeId){
        AssetClass assetClass = new AssetClass();
        assetClass.setId(assetClassId);
        AssetType assetType = new AssetType();
        assetType.setId(assetTypeId);
        Optional<InspectionLists>  inspectinModel = inspectionListRepository.findByMakeAndModelAndAssetClassAndAssetType(make,model,assetClass,assetType);
        return  inspectinModel;
    }

}
