package com.roms.api.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.*;
import com.roms.api.requestInput.EmployeePayLoad;
import com.roms.api.service.EmployeService;
import com.roms.api.service.EmployeeDeviceService;
import com.roms.api.service.NotificationService;
import com.roms.api.service.UserRolesMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;

@Component(value="addusernotification")
public class AdduserNotifcationServiceImpl extends NotificationService {

    @Autowired
    private UserRolesMapService userRolesMapService;
    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @Override
    public void sendNotification(EmployeePayLoad employeePayLoad) throws InterruptedException {


            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            List<Employe> employess = userRolesMapService.findAllEmployeeByRoleName("ROLE_SUPERVISOR",employeePayLoad.getOrgId());
            if (!employess.isEmpty()) {
                for(Employe employes : employess ) {
                    List<String> allDevices = new ArrayList<>();
                    List<EmployeeDevices> notificatinDevices = employeeDeviceService.findAllByEmployee(employes.getId(), employeePayLoad.getOrgId());
                    if (!notificatinDevices.isEmpty()) {
                        notificatinDevices.forEach(obj -> {
                            allDevices.add(obj.getNotificationDeviceToken());
                        });
                    }


                    PushNotificationPayload requestPayload = new PushNotificationPayload();
                    String fromName = employeePayLoad.getFirstName() + " " + employeePayLoad.getLastName();
                    requestPayload.setFrom("");
                    requestPayload.setType("adduser_request");
                    requestPayload.setMessage(fromName + " has applied for registration");
                    requestPayload.setUsername(employes.getId());
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("profileImage", "");
                    String time = String.valueOf(Instant.now().toEpochMilli());
                    obj.put("time", time);
                    obj.put("devices", allDevices);
                    obj.put("eventId", employeePayLoad.getId());
                    requestPayload.setBody(obj);

                    HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);

                    restTemplate.exchange(
                            "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
                    Thread.sleep(500);
                }
            }

    }



}
