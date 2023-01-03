package com.roms.api.controller;

import com.roms.api.model.AssetCategory;
import com.roms.api.model.AssetType;
import com.roms.api.service.AssetCategoryService;
import com.roms.api.service.AssetClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/assetscategory")
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;


    @PostMapping()
    public ResponseEntity<?> saveItemCategory(@RequestBody() AssetCategory request){
        Map<String,Object> response = new HashMap();
        try {
            request.setCode(request.getCode().toLowerCase());
            AssetCategory model = assetCategoryService.save(request);
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
    public ResponseEntity<?> loadAllAssetsCategory() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",assetCategoryService.findAll());
        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
