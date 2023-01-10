package com.roms.api.service;

import com.roms.api.model.AssetClass;
import com.roms.api.model.AssetType;
import com.roms.api.model.InspectionItems;
import com.roms.api.model.InspectionListMapping;
import com.roms.api.repository.InspectionListMappingRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class InspectionListMappingService {

    @Autowired
    private InspectionListMappingRepository inspectionListMappingRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public InspectionListMapping save(InspectionListMapping model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return inspectionListMappingRepository.save(model);
    }

    public List<InspectionItems> findAllItemsByInspection(String make, String model, String assetClassId, String assetTypeId){
        AssetClass assetClass = new AssetClass();
        assetClass.setId(assetClassId);
        AssetType assetType = new AssetType();
        assetType.setId(assetTypeId);
        List<InspectionItems> itemList = new ArrayList<>();

        List<InspectionListMapping> resultList = inspectionListMappingRepository.findAllByInspectionListMakeAndInspectionListModelAndInspectionList_AssetClassAndInspectionList_AssetTypeOrderByInspectionOrderAsc(make,model,assetClass, assetType);
        for( InspectionListMapping obj :resultList ){
            obj.getInspectionOrder();
            obj.getInspectionItems().setInspectionOrder(obj.getInspectionOrder());
            itemList.add(obj.getInspectionItems());
        }
     return  itemList;
    }


}
