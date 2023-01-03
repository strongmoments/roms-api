package com.roms.api.controller;

import com.roms.api.model.Assets;
import com.roms.api.model.InspectionItems;
import com.roms.api.model.InspectionListMapping;
import com.roms.api.model.InspectionLists;
import com.roms.api.service.AssetsService;
import com.roms.api.service.InspectionListMappingService;
import com.roms.api.service.InspectionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/prestart")
public class PrestartController {

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private InspectionListService inspectionListService;
    @Autowired
    private InspectionListMappingService inspectionListMappingService;

    @GetMapping(value = "/sync")
    public ResponseEntity<?> loadAllAssetsClass() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Assets> finalAssetList = new ArrayList<Assets>();
            List<Assets> assetList =assetsService.findAll();
            for (Assets obj : assetList){
                Assets assetModel = new Assets();
                assetModel=obj;
                String assetClass = obj.getAssetClass() == null ? null : obj.getAssetClass().getId();
                String assetType = obj.getAssetType() == null ? null : obj.getAssetType().getId();
                if(obj.getMake() != null && obj.getModel() != null && assetClass != null &&  assetType != null  ){
                    List<InspectionItems> model = inspectionListMappingService.findAllItemsByInspection(obj.getMake(),obj.getModel(),obj.getAssetClass().getId(),obj.getAssetType().getId());
                    if(!model.isEmpty()){
                        assetModel.setItemList(model);
                    }
                }
                finalAssetList.add(assetModel);
            }
            response.put("data",finalAssetList);
        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
