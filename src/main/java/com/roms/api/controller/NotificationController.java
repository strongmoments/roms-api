package com.roms.api.controller;

import com.roms.api.model.LeaveRequest;
import com.roms.api.model.PushNotificationPayload;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/notification")
public class NotificationController {

    public static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    @Qualifier("default")
    private NotificationService notificationService;

    @PostMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> delete(@RequestBody PushNotificationPayload leaveRequest) throws ParseException {
        Map<String,Object> response = new HashMap<>();
        try {
            notificationService.deleteNotification(leaveRequest.getEventId());
        }catch (Exception e){

            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("status","success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/load")
    public ResponseEntity<?> loadNotification(){
        Map<String,Object> response = new HashMap<>();
        List<Object> objectList = new ArrayList<Object>();
        try {
            Map<String,Object> responseMap =   notificationService.loadNotification();
            responseMap.forEach( (k, v)->{
                objectList.add(v);
                    }
            );
            response.put("data",objectList);
        } catch (Exception e){
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
