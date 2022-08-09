package com.roms.api.controller;

import com.roms.api.model.LeaveRequest;
import com.roms.api.model.Users;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/leave")
public class LeaveRequestController {


    @PostMapping(value = "/request", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> requestLeave(@RequestBody LeaveRequest leaveRequest) {

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
}
