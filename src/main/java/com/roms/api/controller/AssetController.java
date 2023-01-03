package com.roms.api.controller;

import com.roms.api.model.*;
import com.roms.api.service.*;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/assets")
public class AssetController {

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private AssetCategoryService assetCategoryService;

    @Autowired
    private AssetTypeService assetTypeService;

    @Autowired
    private AssetClassService assetClassService;

    @Autowired
    private LocationService locationService;


    @PostMapping()
    public ResponseEntity<?> saveCirtificate(@RequestBody() Assets assets ){
        Map<String,Object> response = new HashMap();
        try {

            if(StringUtils.isNotBlank(assets.getQrCodeId())){
                DigitalAssets QR = new DigitalAssets();
                QR.setId(assets.getQrCodeId());
                assets.setQrCode(QR);
            }
            if(StringUtils.isNotBlank(assets.getAssetImageId())){
                DigitalAssets assetImage = new DigitalAssets();
                assetImage.setId(assets.getAssetImageId());
               
                assets.setAssetImage(assetImage);
            }
            if(StringUtils.isBlank(assets.getAssetCategory().getId())){
                AssetCategory assetCategory=  assetCategoryService.save(assets.getAssetCategory());
                assets.setAssetCategory(assetCategory);
            }else{
                AssetCategory assetCategory = new AssetCategory();
                assetCategory.setId(assets.getAssetCategory().getId());
                assets.setAssetCategory(assetCategory);
            }

            if(StringUtils.isBlank(assets.getAssetType().getId())){
                AssetType assetType=  assetTypeService.save(assets.getAssetType());
                assets.setAssetType(assetType);
            }else{
                AssetType assetType = new AssetType();
                assetType.setId(assets.getAssetType().getId());
                assets.setAssetType(assetType);

            }

            if(StringUtils.isBlank(assets.getAssetClass().getId())){
                AssetClass assetClass =  assetClassService.save(assets.getAssetClass());
                assets.setAssetClass(assetClass);
            }else{
                AssetClass assetClass  = new AssetClass();
                assetClass.setId(assets.getAssetClass().getId());
                assets.setAssetClass(assetClass);
            }

            if(StringUtils.isBlank(assets.getLocation().getId())){
                Location location =  locationService.save(assets.getLocation());
                assets.setLocation(location);
            }else{
                Location location = new Location();
                location.setId(assets.getLocation().getId());
                assets.setLocation(location);
            }

            assets.setMake(assets.getMake().toUpperCase());
            assets.setModel(assets.getModel().toUpperCase());
             assets =assetsService.save(assets);
            response.put("status","success");
            response.put("id",assets.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> loadApliedLeaveByLeaveStatus(
            @RequestParam(value = "class", defaultValue = "") String className,
            @RequestParam(value = "type", defaultValue = "0") int type,
            @RequestParam(value = "status", defaultValue = "0") int status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {
        Map<String, Object> response = new HashMap<>();
        try {
            Page<Assets> requestedPage = null;
                requestedPage = assetsService.findAll(page,size);
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e) {
         //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{assetId}")
    public ResponseEntity<?> loadApliedLeaveByLeaveStatus(
            @RequestParam(value = "assetId", defaultValue = "") String assetId) {
        Map<String, Object> response = new HashMap<>();
        try {

            Optional<Assets> requestedPage =  assetsService.findById(assetId);
           if(requestedPage.isPresent()){
               response.put("data", requestedPage.get());
           }else{
               response.put("error", "asset_not_found");
               return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
           }

        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
