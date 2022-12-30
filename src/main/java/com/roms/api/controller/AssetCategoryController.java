package com.roms.api.controller;

import com.roms.api.service.AssetCategoryService;
import com.roms.api.service.AssetClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/assetscategory")
public class AssetCategoryController {

    @Autowired
    private AssetCategoryService assetCategoryService;

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
