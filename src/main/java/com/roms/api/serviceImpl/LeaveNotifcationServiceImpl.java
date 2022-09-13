package com.roms.api.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.EmployeeDevices;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.LeaveType;
import com.roms.api.model.PushNotificationPayload;
import com.roms.api.service.EmployeeDeviceService;
import com.roms.api.service.LeaveRequestService;
import com.roms.api.service.LeaveTypeService;
import com.roms.api.service.NotificationService;
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

@Component(value="leavenotification")
public class LeaveNotifcationServiceImpl extends NotificationService {


    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @Autowired
    private LeaveTypeService leaveTypeService;

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Override
    public void sendNotification(String eventId) {

       Optional<LeaveRequest> leaveRequests =leaveRequestService.findById(eventId);
       if(leaveRequests.isPresent()){
           LeaveRequest leaveRequest = leaveRequests.get();
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
           Optional<LeaveType> leaveType = leaveTypeService.findById(leaveRequest.getLeaveType().getId());
           requestPayload.setType("leave_request");
           requestPayload.setMessage(fromName+" applied for "+(leaveType.isEmpty()  ?"" :leaveType.get().getLeaveDescription()).toLowerCase()+" leave");
           requestPayload.setUsername(leaveRequest.getApprover().getId());
           Map<String ,Object> obj = new HashMap<>();
           obj.put("profileImage",loggedIn.getUser().getEmployeId().getProfileImage());
           String time = String.valueOf(Instant.now().toEpochMilli());
           obj.put("time", time);
           obj.put("devices",allDevices);
           obj.put("eventId",leaveRequest.getId());
           requestPayload.setBody(obj);

           HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);

           restTemplate.exchange(
                   "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();


       }


    }

    @Override
    public void sendApprovedOrRejectNotification(String eventId, String message, String type) {
        Optional<LeaveRequest> leaveRequests =leaveRequestService.findById(eventId);
        if(leaveRequests.isPresent()) {
            LeaveRequest leaveRequest = leaveRequests.get();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            List<EmployeeDevices> notificatinDevices = employeeDeviceService.findAllByEmployee(leaveRequest.getEmploye().getId());
            List<String> allDevices = new ArrayList<>();
            notificatinDevices.forEach(obj -> {
                allDevices.add(obj.getNotificationDeviceToken());
            });

            PushNotificationPayload requestPayload = new PushNotificationPayload();
            String fromName = loggedIn.getUser().getEmployeId().getFirstName() + " " + loggedIn.getUser().getEmployeId().getLastName();
            requestPayload.setFrom(loggedIn.getUser().getEmployeId().getId());
            requestPayload.setType("leave_" + type);
            Optional<LeaveType> leaveType = leaveTypeService.findById(leaveRequest.getLeaveType().getId());
            requestPayload.setMessage(fromName + " " + message + " " + (leaveType.isEmpty() ? "" : leaveType.get().getLeaveDescription()).toLowerCase() + " leave");

            requestPayload.setUsername(leaveRequest.getEmploye().getId());
            Map<String, Object> obj = new HashMap<>();
            obj.put("profileImage", loggedIn.getUser().getEmployeId().getProfileImage());
            String time = String.valueOf(Instant.now().toEpochMilli());
            obj.put("time", time);
            obj.put("devices", allDevices);
            obj.put("eventId", leaveRequest.getId());

            requestPayload.setBody(obj);

            HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);

            restTemplate.exchange(
                    "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
        }else {

        }

    }





}
