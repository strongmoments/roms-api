package com.roms.api.controller;

import com.roms.api.model.AssetClass;
import com.roms.api.model.ItemCategory;
import com.roms.api.service.AssetClassService;
import com.roms.api.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/assetsclass")
public class AssetClassController {

    @Autowired
    private AssetClassService assetClassService;


    @PostMapping()
    public ResponseEntity<?> saveItemCategory(@RequestBody() AssetClass request){
        Map<String,Object> response = new HashMap();
        try {
            request.setCode(request.getCode().toLowerCase());
            AssetClass model = assetClassService.save(request);
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
    public ResponseEntity<?> loadAllAssetsClass() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",assetClassService.findAll());
        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
