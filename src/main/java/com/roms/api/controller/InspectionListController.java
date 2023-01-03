package com.roms.api.controller;

import com.roms.api.model.InspectionItems;
import com.roms.api.model.InspectionListMapping;
import com.roms.api.model.InspectionLists;
import com.roms.api.model.ItemCategory;
import com.roms.api.requestInput.InspectionListInput;
import com.roms.api.service.InspectionListMappingService;
import com.roms.api.service.InspectionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/inspectionlist")
public class InspectionListController {

    @Autowired
    private InspectionListService inspectionListService;

    @Autowired
    private InspectionListMappingService inspectionListMappingService;


    @PostMapping()
    public ResponseEntity<?> saveInspectionList(@RequestBody() InspectionLists request){
        Map<String,Object> response = new HashMap();
        try {
            request.setMake(request.getMake().toUpperCase());
            request.setModel(request.getModel().toUpperCase());
            InspectionLists model = inspectionListService.save(request);
            response.put("status","success");
            response.put("id",model.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/mapping")
    public ResponseEntity<?> assigneInspectionItem(@RequestBody() InspectionListInput request){
        Map<String,Object> response = new HashMap();
        try {

            List<InspectionItems> allItems = request.getItems();
            for(InspectionItems obj : allItems){
                InspectionLists inspectionLists = new InspectionLists();
                inspectionLists.setId(request.getInspectionListId());
                InspectionListMapping mapping = new InspectionListMapping();
                mapping.setInspectionList(inspectionLists);
                mapping.setInspectionItems(obj);
                inspectionListMappingService.save(mapping);
            }
            response.put("status","success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<?> loadInspectionList() throws ChangeSetPersister.NotFoundException {
        Map<String,Object> response = new HashMap();
        List<InspectionLists> requestedPage =  inspectionListService.findAll();
        response.put("data",requestedPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
