package com.roms.api.controller;

import com.roms.api.model.EmployeeSkilsCirtificate;
import com.roms.api.model.ItemCategory;
import com.roms.api.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping(value = "/v1/inspection/item/category")
public class ItemCategoryController {

    @Autowired
    private ItemCategoryService itemCategoryService;

    @PostMapping()
    public ResponseEntity<?> saveItemCategory(@RequestBody()ItemCategory request){
        Map<String,Object> response = new HashMap();
        try {
            request.setCode(request.getCode().toLowerCase());
            ItemCategory model = itemCategoryService.save(request);
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
    public ResponseEntity<?> loadItemCategory() throws ChangeSetPersister.NotFoundException {
        Map<String,Object> response = new HashMap();
        List<ItemCategory> requestedPage =  itemCategoryService.findAll();
        response.put("data",requestedPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
