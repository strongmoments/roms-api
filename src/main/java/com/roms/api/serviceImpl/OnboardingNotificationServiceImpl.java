package com.roms.api.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeDevices;
import com.roms.api.model.PushNotificationPayload;
import com.roms.api.requestInput.EmployeePayLoad;
import com.roms.api.service.EmployeeDeviceService;
import com.roms.api.service.EmployeeOnboardingService;
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

@Component(value="onboardingnotification")
public class OnboardingNotificationServiceImpl extends NotificationService {

    @Autowired
    private UserRolesMapService userRolesMapService;
    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @Autowired
    private  EmployeeOnboardingService employeeOnboardingService;
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
                ObjectMapper jsonParser =new ObjectMapper();
                String responses =   employeeOnboardingService.loadByEmployeId();
                Map<String,Object> object = new HashMap<>();
                try {
                object = jsonParser.readValue(responses, HashMap.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                PushNotificationPayload requestPayload = new PushNotificationPayload();
                String fromName = employeePayLoad.getFirstName() + " " + employeePayLoad.getLastName();
                requestPayload.setFrom("");
                requestPayload.setType("onboarding_status");
                requestPayload.setMessage(fromName + " has completed onboarding.");
                requestPayload.setUsername(employes.getId());
                Map<String, Object> obj = new HashMap<>();
                obj.put("profileImage", "");
                String time = String.valueOf(Instant.now().toEpochMilli());
                obj.put("time", time);
                obj.put("devices", allDevices);
                obj.put("eventId", employeePayLoad.getId());
                obj.put("data",object);
                requestPayload.setBody(obj);

                HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);

                restTemplate.exchange(
                        "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
                Thread.sleep(500);
            }
        }

    }
}
