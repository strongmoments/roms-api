package com.roms.api.controller;

import com.roms.api.model.*;
import com.roms.api.requestInput.PrestartInput;
import com.roms.api.service.*;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
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

    @Autowired
    private PrestartDetailService prestartDetailService;

    @Autowired
    private PrestartService prestartService;

    @PostMapping()
    public ResponseEntity<?> savePrestart(@RequestBody() PrestartInput request){
        Map<String,Object> response = new HashMap();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss", Locale.getDefault());

            Prestart prestart = new Prestart();
            if(StringUtils.isNotBlank(request.getId())){
                prestartDetailService.deletePrestartDetailsByPrestartId(request.getId());
                prestart.setId(request.getId());
            }
            Instant date = sdf.parse(request.getDate()).toInstant();
            Instant startTime = sdf.parse(request.getStartTime()).toInstant();
            Instant endTime = sdf.parse(request.getEndTime()).toInstant();
            prestart.setHasDefect(false);
            prestart.setStartTime(startTime);
            prestart.setDate(date);
            prestart.setEndTime(endTime);

            if(StringUtils.isNotBlank(request.getAssetId())){
                Assets asset = new Assets();
                asset.setId(request.getAssetId());
                prestart.setAssets(asset);

            }

            if(StringUtils.isNotBlank(request.getEmployeeSelfieImageId())){
                DigitalAssets employeeSelfie  = new DigitalAssets();
                employeeSelfie.setId(request.getEmployeeSelfieImageId());
                prestart.setEmployeeSelfie(employeeSelfie);
            }

            prestart.setPrestartLocation(request.getPrestartLocation());
            prestart.setLocationLatLong(request.getLocationLatLong());

            prestart = prestartService.save(prestart);


           List<PrestartDetails> prestartList = request.getPrestartDetails();
           boolean hasDefect = false;
            if(prestartList != null && !prestartList.isEmpty()){

                for(PrestartDetails model : prestartList){
                    model.setPrestart(prestart);
                     if(model.getAnswer() == 2){
                         hasDefect = true;
                     }
                    if(StringUtils.isNotBlank(model.getDefectMediaId())){
                        DigitalAssets defectMedia  =  new DigitalAssets();
                        defectMedia.setId(model.getDefectMediaId());
                        model.setDifectMedia(defectMedia);;
                    }

                    prestartDetailService.save(model);
                }

            }
            if(hasDefect){
                prestart.setHasDefect(true);
                prestart = prestartService.save(prestart);
            }

            response.put("status","success");
            response.put("id",prestart.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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

    @GetMapping(value = "")
    public ResponseEntity<?> loadAllPrestart() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Prestart> dataList = prestartService.findAll();

            response.put("data",dataList);
        } catch (Exception e) {
            //   logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
