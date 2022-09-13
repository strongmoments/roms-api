package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.EmployeeResignation;
import com.roms.api.model.LeaveRequest;
import com.roms.api.service.EmployeeResignationService;
import com.roms.api.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/resignation")
public class ResignationController {

    public static final Logger logger = LoggerFactory.getLogger(ResignationController.class);

    @Autowired
    private EmployeeResignationService employeeResignationService;

    @Autowired
    @Qualifier("resignenotification")
    private NotificationService notificationService;

    @RequestMapping(value = "/apply" , method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE  })
    public ResponseEntity<?> submitResignation(@RequestParam String inputJsonString, @RequestParam(value="files") MultipartFile file) throws IOException {

        Map<String,Object> response = new HashMap();
        try {
            ObjectMapper mapper  = new ObjectMapper();
            Map<String, MultipartFile> imageData = new HashMap();
            EmployeeResignation employeeResignation = mapper.readValue(inputJsonString, EmployeeResignation.class);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Instant lastWorkingDate = sdf.parse(employeeResignation.getStrLastWorkingDate()).toInstant();
            employeeResignation.setLastWorkingDate(lastWorkingDate);
            employeeResignation = employeeResignationService.resigne(employeeResignation);
            if(employeeResignation != null && employeeResignation.getId() != null)
                notificationService.sendNotification(employeeResignation.getId());
            response.put("status","success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/approve", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> approveResignation(@RequestBody EmployeeResignation employeeResignation) {
        Map<String,Object> response = new HashMap<>();
        try {
            employeeResignation = employeeResignationService.approveResignation(employeeResignation);
            if(employeeResignation != null && employeeResignation.getId() != null)
                notificationService.sendApprovedOrRejectNotification(employeeResignation.getId(), "approved your", "approve");
        }catch (Exception e){
            logger.error("Error while approving resigne {} ",  e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status","success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/reject", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> rejectResignation(@RequestBody EmployeeResignation employeeResignation) {
        Map<String,Object> response = new HashMap<>();
        try {
            employeeResignation = employeeResignationService.resectResignation(employeeResignation);
            if(employeeResignation != null && employeeResignation.getId() != null)
                notificationService.sendApprovedOrRejectNotification(employeeResignation.getId(), "rejected your", "reject");
        }catch (Exception e){
            logger.error("Error while rejecting resigne {} ",  e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("status","success");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping(value = "/applied")
    public ResponseEntity<?> loadAppliedResignation(){
        Map<String, Object> response = new HashMap<>();
        try {
            Page<LeaveRequest> requestedPage = null;
            response.put("data",employeeResignationService.findAppliedResignation().get());
        } catch (Exception e){
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status","error");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/appliedToMe")
    public ResponseEntity<?> loadApliedToMeResignation(
            @RequestParam(value ="status", defaultValue = "0") int resigneStatus,
            @RequestParam(value ="page", defaultValue = "0") int page,
            @RequestParam(value ="size", defaultValue = "3") int size){



        Map<String, Object> response = new HashMap<>();
        try {
            Page<EmployeeResignation> requestedPage = null;
            if(resigneStatus == 0){
                requestedPage = employeeResignationService.findAllRecievedRequest( page, size);
            }else{
                requestedPage = employeeResignationService.findAllRecievedRequestByLeaveStatus(page, size,resigneStatus);
            }
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

    @GetMapping(value = "/appliedToMeHistory")
    public ResponseEntity<?> loadApliedToMeLeaveHistory(
            @RequestParam(value ="page", defaultValue = "0") int page,
            @RequestParam(value ="size", defaultValue = "3") int size){

        Map<String, Object> response = new HashMap<>();
        try {
            Page<EmployeeResignation> requestedPage = null;

            requestedPage = employeeResignationService.findAllRecievedRequestHistory( page, size);

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

