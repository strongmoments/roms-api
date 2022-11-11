package com.roms.api.controller;

import com.roms.api.model.Assets;
import com.roms.api.model.Client;
import com.roms.api.model.DigitalAssets;
import com.roms.api.model.LeaveRequest;
import com.roms.api.service.AssetsService;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/assets")
public class AssetController {

    @Autowired
    private AssetsService assetsService;

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
}
