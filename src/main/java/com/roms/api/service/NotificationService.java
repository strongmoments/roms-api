package com.roms.api.service;

import com.roms.api.model.LeaveRequest;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {



    @Autowired
    private LoggedInUserDetails loggedIn;

    public  void sendLeaveRequestNotification(LeaveRequest leaveRequest){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        PushNotificationPayload requestPayload = new PushNotificationPayload();
        String fromName  = loggedIn.getUser().getEmployeId().getFirstName() +" "+loggedIn.getUser().getEmployeId().getLastName();
        requestPayload.setFrom(loggedIn.getUser().getEmployeId().getEmail());
        requestPayload.setType("leave_request");
        requestPayload.setMessage(fromName+"  applied for "+leaveRequest.getLeaveType().getLeaveDescription()+" leave");
        requestPayload.setUsername(leaveRequest.getApprover().getEmail());
        Map<String ,Object> obj = new HashMap<>();
        obj.put("profileImage",loggedIn.getUser().getEmployeId().getProfileImage());
        obj.put("time", Instant.now());
        requestPayload.setBody(obj);

        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);

        restTemplate.exchange(
                "http://localhost:8081/notification", HttpMethod.POST, entity, String.class).getBody();


    }

    public  void sendLeaveApprovedNotification(LeaveRequest leaveRequest, String message,String type){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        PushNotificationPayload requestPayload = new PushNotificationPayload();
        String fromName  = loggedIn.getUser().getEmployeId().getFirstName() +" "+loggedIn.getUser().getEmployeId().getLastName();
        requestPayload.setFrom(loggedIn.getUser().getEmployeId().getEmail());
        requestPayload.setType("leave_"+type);
        requestPayload.setMessage(fromName+" "+message+" "+leaveRequest.getLeaveType().getLeaveDescription()+" leave");
        requestPayload.setUsername(leaveRequest.getApprover().getEmail());
        Map<String ,Object> obj = new HashMap<>();
        obj.put("profileImage",loggedIn.getUser().getEmployeId().getProfileImage());
        obj.put("time", Instant.now());
        requestPayload.setBody(obj);

        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);

        restTemplate.exchange(
                "http://localhost:8081/notification", HttpMethod.POST, entity, String.class).getBody();


    }


}
