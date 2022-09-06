package com.roms.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.EmployeeDevices;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.LeaveType;
import com.roms.api.model.PushNotificationPayload;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;

@Service
public class NotificationService {



    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @Autowired
    private LeaveTypeService leaveTypeService;



    public  void sendLeaveRequestNotification(LeaveRequest leaveRequest){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        List<EmployeeDevices> notificatinDevices = employeeDeviceService.findAllByEmployee(leaveRequest.getApprover().getId());
        List<String> allDevices = new ArrayList<>();
        notificatinDevices.forEach(obj->{
            allDevices.add(obj.getNotificationDeviceToken());
        });
        PushNotificationPayload requestPayload = new PushNotificationPayload();
        String fromName  = loggedIn.getUser().getEmployeId().getFirstName() +" "+loggedIn.getUser().getEmployeId().getLastName();
        requestPayload.setFrom(loggedIn.getUser().getEmployeId().getId());
        Optional<LeaveType>  leaveType = leaveTypeService.findById(leaveRequest.getLeaveType().getId());
        requestPayload.setType("leave_request");
        requestPayload.setMessage(fromName+"  applied for "+(leaveType.isEmpty()  ?"" :leaveType.get().getLeaveDescription())+" leave");
        requestPayload.setUsername(leaveRequest.getApprover().getId());
        Map<String ,Object> obj = new HashMap<>();
        obj.put("profileImage",loggedIn.getUser().getEmployeId().getProfileImage());
        obj.put("time", Instant.now());
        obj.put("devices",allDevices);
        requestPayload.setBody(obj);

        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);

        restTemplate.exchange(
                "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();


    }

    public  void sendLeaveApprovedNotification(LeaveRequest leaveRequest, String message,String type){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        List<EmployeeDevices> notificatinDevices = employeeDeviceService.findAllByEmployee(leaveRequest.getEmploye().getId());
        List<String> allDevices = new ArrayList<>();
        notificatinDevices.forEach(obj->{
            allDevices.add(obj.getNotificationDeviceToken());
        });

        PushNotificationPayload requestPayload = new PushNotificationPayload();
        String fromName  = loggedIn.getUser().getEmployeId().getFirstName() +" "+loggedIn.getUser().getEmployeId().getLastName();
        requestPayload.setFrom(loggedIn.getUser().getEmployeId().getId());
        requestPayload.setType("leave_"+type);
        Optional<LeaveType>  leaveType = leaveTypeService.findById(leaveRequest.getLeaveType().getId());
        requestPayload.setMessage(fromName+" "+message+" "+(leaveType.isEmpty()  ?"" :leaveType.get().getLeaveDescription())+" leave");

        requestPayload.setUsername(leaveRequest.getEmploye().getId());
        Map<String ,Object> obj = new HashMap<>();
        obj.put("profileImage",loggedIn.getUser().getEmployeId().getProfileImage());
        obj.put("time", Instant.now());
        obj.put("devices",allDevices);
        requestPayload.setBody(obj);

        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);

        restTemplate.exchange(
                "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();


    }

    public Map<String,Object>  loadNotification() throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        PushNotificationPayload requestPayload = new PushNotificationPayload();
        requestPayload.setUsername(loggedIn.getUser().getEmployeId().getId());
        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);
       String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/loadNotification", HttpMethod.POST, entity, String.class).getBody();

        ObjectMapper obj = new ObjectMapper();
        Map<String,Object>  response = obj.readValue(responseAsStrign, HashMap.class);
        return response;
    }

    public boolean deleteNotification(String eventId) throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        PushNotificationPayload requestPayload = new PushNotificationPayload();
        requestPayload.setUsername(loggedIn.getUser().getEmployeId().getId());
        requestPayload.setEventId(eventId);
        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);
        String responseAsStrign =  restTemplate.exchange( "http://localhost:8081/deleteNotification", HttpMethod.POST, entity, String.class).getBody();

        return true;
    }

}
