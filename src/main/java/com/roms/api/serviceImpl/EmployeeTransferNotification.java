package com.roms.api.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.*;
import com.roms.api.requestInput.OnboardingCompleteInput;
import com.roms.api.service.EmployeService;
import com.roms.api.service.EmployeeDeviceService;
import com.roms.api.service.EmployeeManagerService;
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

@Component(value="employeeTransfernotification")
public class EmployeeTransferNotification extends NotificationService {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @Autowired
    private LoggedInUserDetails loggedIn;


    @Override
    public void sendNotification(EmployeeResourcedemand demandPayload,String startDate) throws InterruptedException {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    List<Employe> employess = employeService.findAllManagerss();

        if(!employess.isEmpty())

    {
        ObjectMapper jsonParser = new ObjectMapper();
        Map<String, Object> object = new HashMap<>();

        for (Employe employes : employess) {
            List<String> allDevices = new ArrayList<>();
            List<EmployeeDevices> notificatinDevices = employeeDeviceService.findAllByEmployee(employes.getId());
            if (!notificatinDevices.isEmpty()) {
                notificatinDevices.forEach(obj -> {
                    allDevices.add(obj.getNotificationDeviceToken());
                });
            }

            PushNotificationPayload requestPayload = new PushNotificationPayload();

            requestPayload.setFrom("");
            requestPayload.setType("job_demand");
            requestPayload.setMessage(demandPayload.getRoleName()+" demand posted by "+demandPayload.getHiringManager().getFirstName()+" "+demandPayload.getHiringManager().getLastName()+" with Proposed Start Date -"+startDate+".");
            requestPayload.setUsername(employes.getId());
            Map<String, Object> obj = new HashMap<>();
            Set<EmployeeProfileImage>  image = demandPayload.getHiringManager().getProfileImage();
            String profileImageUrl = "";
            if(image != null){
                for (EmployeeProfileImage data : image){
                    profileImageUrl = data.getDigitalAssets().getUrl();
                }
            }
            obj.put("profileImage", profileImageUrl);
            String time = String.valueOf(Instant.now().toEpochMilli());
            obj.put("time", time);
            obj.put("devices", allDevices);
            obj.put("eventId", demandPayload.getDemandId());
            obj.put("data", object);
            requestPayload.setBody(obj);
            HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);
            restTemplate.exchange(
                    "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
            Thread.sleep(500);
        }
    }
}
    @Override
    public void sendRecomendNotification(EmploymentRecommendation recomendNotifcation)throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ObjectMapper jsonParser = new ObjectMapper();
        Map<String, Object> object = new HashMap<>();
        List<String> allDevices = new ArrayList<>();
        List<EmployeeDevices> employeeDevices = employeeDeviceService.findAllByEmployee(recomendNotifcation.getDemandIdx().getHiringManager().getId());
        if (!employeeDevices.isEmpty()) {
            employeeDevices.forEach(obj -> {
                allDevices.add(obj.getNotificationDeviceToken());
            });

            PushNotificationPayload requestPayload = new PushNotificationPayload();

            requestPayload.setFrom("");
            requestPayload.setType("employee_recommend");
            requestPayload.setMessage(recomendNotifcation.getEmployeeIdx().getFirstName() + " " + recomendNotifcation.getEmployeeIdx().getLastName() + " is recommended by " + recomendNotifcation.getInitiatedBy().getFirstName() + " " + recomendNotifcation.getInitiatedBy().getLastName() + " for " + recomendNotifcation.getDemandIdx().getRoleName() + " demand.");
            requestPayload.setUsername(recomendNotifcation.getDemandIdx().getHiringManager().getId());
            Map<String, Object> obj = new HashMap<>();
            Set<EmployeeProfileImage> image = recomendNotifcation.getInitiatedBy().getProfileImage();
            String profileImageUrl = "";
            if (image != null) {
                for (EmployeeProfileImage data : image) {
                    profileImageUrl = data.getDigitalAssets().getUrl();
                }
            }
            obj.put("profileImage", profileImageUrl);
            String time = String.valueOf(Instant.now().toEpochMilli());
            obj.put("time", time);
            obj.put("devices", allDevices);
            obj.put("eventId", recomendNotifcation.getId());
            obj.put("data", object);
            requestPayload.setBody(obj);
            HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);
            restTemplate.exchange(
                    "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
            Thread.sleep(500);
        }
    }

    @Override
    public void sendRecomendationApproveOrRejectNotification(EmploymentRecommendation recomendNotifcation,String status)throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ObjectMapper jsonParser = new ObjectMapper();
        Map<String, Object> object = new HashMap<>();
        List<String> allDevices = new ArrayList<>();
        List<EmployeeDevices> employeeDevices = employeeDeviceService.findAllByEmployee(recomendNotifcation.getInitiatedBy().getId());
        if (!employeeDevices.isEmpty()) {
            employeeDevices.forEach(obj -> {
                allDevices.add(obj.getNotificationDeviceToken());
            });

            PushNotificationPayload requestPayload = new PushNotificationPayload();

            requestPayload.setFrom("");
            requestPayload.setType("employee_recommend");
            if(status.equalsIgnoreCase("approve")){
                requestPayload.setMessage(recomendNotifcation.getEmployeeIdx().getFirstName() + " " + recomendNotifcation.getEmployeeIdx().getLastName() + " is accepted by " + recomendNotifcation.getDemandIdx().getHiringManager().getFirstName() + " " + recomendNotifcation.getDemandIdx().getHiringManager().getLastName() + " for " + recomendNotifcation.getDemandIdx().getRoleName() + " demand.");
            }
            if(status.equalsIgnoreCase("reject")){
                requestPayload.setMessage(recomendNotifcation.getEmployeeIdx().getFirstName() + " " + recomendNotifcation.getEmployeeIdx().getLastName() + " is rejected by " + recomendNotifcation.getDemandIdx().getHiringManager().getFirstName() + " " + recomendNotifcation.getDemandIdx().getHiringManager().getLastName() + " for " + recomendNotifcation.getDemandIdx().getRoleName() + " demand.");
            }
            requestPayload.setUsername(recomendNotifcation.getInitiatedBy().getId());
            Map<String, Object> obj = new HashMap<>();
            Set<EmployeeProfileImage> image = recomendNotifcation.getDemandIdx().getHiringManager().getProfileImage();
            String profileImageUrl = "";
            if (image != null) {
                for (EmployeeProfileImage data : image) {
                    profileImageUrl = data.getDigitalAssets().getUrl();
                }
            }
            obj.put("profileImage", profileImageUrl);
            String time = String.valueOf(Instant.now().toEpochMilli());
            obj.put("time", time);
            obj.put("devices", allDevices);
            obj.put("eventId", recomendNotifcation.getId());
            obj.put("data", object);
            requestPayload.setBody(obj);
            HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload, headers);
            restTemplate.exchange(
                    "http://localhost:8081/sendNotification", HttpMethod.POST, entity, String.class).getBody();
            Thread.sleep(500);
        }
    }



}
