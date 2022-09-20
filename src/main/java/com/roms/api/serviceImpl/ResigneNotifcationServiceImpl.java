package com.roms.api.serviceImpl;

import com.roms.api.model.*;
import com.roms.api.service.*;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;

@Component(value="resignenotification")
public class ResigneNotifcationServiceImpl extends NotificationService {



    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @Autowired
    private EmployeeResignationService employeeResignationService;


    @Override
    public void sendNotification(String eventId) {

        Optional<EmployeeResignation> employeeResignations= employeeResignationService.findById(eventId);
        if(employeeResignations.isPresent()){
            EmployeeResignation employeeResignation = employeeResignations.get();
            List<String> allDevices = employeeDeviceService.findAllResisterdDeviceOfEmployee(employeeResignation.getApprover().getId(),loggedIn.getOrg().getId());
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            PushNotificationPayload requestPayload = new PushNotificationPayload();
            String fromName  = loggedIn.getUser().getEmployeId().getFirstName() +" "+loggedIn.getUser().getEmployeId().getLastName();
            requestPayload.setFrom(loggedIn.getUser().getEmployeId().getId());

            requestPayload.setType("resigne_request");
            requestPayload.setMessage(fromName+" has resigned.");
            requestPayload.setUsername(employeeResignation.getApprover().getId());
            Map<String ,Object> obj = new HashMap<>();
            obj.put("profileImage",loggedIn.getUser().getEmployeId().getProfileImage());
            String time = String.valueOf(Instant.now().toEpochMilli());
            obj.put("time", time);
            obj.put("devices",allDevices);
            obj.put("eventId",eventId);
            requestPayload.setBody(obj);

            HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);

            restTemplate.exchange(
                    "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();


        }


    }

    @Override
    public void sendApprovedOrRejectNotification(String eventId, String message, String type) {
        Optional<EmployeeResignation> employeeResignations= employeeResignationService.findById(eventId);
        if(employeeResignations.isPresent()){
            EmployeeResignation employeeResignation = employeeResignations.get();
            List<String> allDevices = employeeDeviceService.findAllResisterdDeviceOfEmployee(employeeResignation.getEmployee().getId(),loggedIn.getOrg().getId());
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            PushNotificationPayload requestPayload = new PushNotificationPayload();
            String fromName = loggedIn.getUser().getEmployeId().getFirstName() + " " + loggedIn.getUser().getEmployeId().getLastName();
            requestPayload.setFrom(loggedIn.getUser().getEmployeId().getId());
            requestPayload.setType("resigne_" + type);
            requestPayload.setMessage(fromName + " " + message + " resignation");
            requestPayload.setUsername(employeeResignation.getEmployee().getId());
            Map<String, Object> obj = new HashMap<>();
            obj.put("profileImage", loggedIn.getUser().getEmployeId().getProfileImage());
            String time = String.valueOf(Instant.now().toEpochMilli());
            obj.put("time", time);
            obj.put("devices", allDevices);
            obj.put("eventId", eventId);
            requestPayload.setBody(obj);
            HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);
            restTemplate.exchange(
                    "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
        }else {

        }

    }

}
