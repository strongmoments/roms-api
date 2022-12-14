package com.roms.api.controller;

import com.roms.api.model.AssetClass;
import com.roms.api.model.AssetType;
import com.roms.api.model.Assets;
import com.roms.api.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/assetstype")
public class AssetTypeController {

    @Autowired
    private AssetTypeService assetTypeService;


    @PostMapping()
    public ResponseEntity<?> saveItemCategory(@RequestBody() AssetType request){
        Map<String,Object> response = new HashMap();
        try {
            request.setCode(request.getCode().toLowerCase());
            AssetType model = assetTypeService.save(request);
            response.put("status","success");
            response.put("id",model.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> loadApliedLeaveByLeaveStatus() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",assetTypeService.findAll());
        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
