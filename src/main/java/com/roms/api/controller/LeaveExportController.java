package com.roms.api.controller;

import com.roms.api.model.LeaveExportHistory;
import com.roms.api.model.LeaveRequest;
import com.roms.api.requestInput.LeaveRequestSearchInput;
import com.roms.api.service.LeaveExportHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/leaveexport")
public class LeaveExportController {
    public static final Logger logger = LoggerFactory.getLogger(LeaveExportController.class);

    @Autowired
    private LeaveExportHistoryService leaveExportHistoryService;

    @PostMapping(value = "/addhistory", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> requestLeave(@RequestBody LeaveExportHistory leaveExportHistory) throws ParseException {
        Map<String,Object> response = new HashMap<>();
        try {
            leaveExportHistoryService.save(leaveExportHistory);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error while adding leave export history {} ",  e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status","success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/loadhistory")
    public ResponseEntity<?> loadAll(
            @RequestParam(value ="page", defaultValue = "0") int page,
            @RequestParam(value ="size", defaultValue = "3") int size){

        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveExportHistory> requestedPage = null;
            requestedPage = leaveExportHistoryService.findAll(page,size);
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e){
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status","error");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
