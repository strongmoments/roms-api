package com.roms.api.controller;

import com.roms.api.model.Employe;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.Users;
import com.roms.api.service.LeaveRequestService;
import com.roms.api.service.LeaveTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/leave")
public class LeaveRequestController {
    public static final Logger logger = LoggerFactory.getLogger(LeaveRequestController.class);

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @PostMapping(value = "/request", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> requestLeave(@RequestBody LeaveRequest leaveRequest) {

        Instant startDate = leaveRequest.getStartDateTime();


        return  null;
    }

    @PostMapping(value = "/approve", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> approveLeave(@RequestBody LeaveRequest leaveRequest) {

        return  null;
    }

    @PostMapping(value = "/reject", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> rejectLeave(@RequestBody LeaveRequest leaveRequest) {

        return  null;
    }

    @GetMapping(value = "/type", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> loadLeaveType() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",leaveTypeService.findAll());
        } catch (Exception e){
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status","error");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
