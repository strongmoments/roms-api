package com.roms.api.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.PushNotificationPayload;
import com.roms.api.service.NotificationService;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component(value="default")
public class NotificationServiceImpl extends NotificationService {

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Override
    public Map<String,Object> loadNotification() throws JsonProcessingException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        PushNotificationPayload requestPayload = new PushNotificationPayload();
        requestPayload.setUsername(loggedIn.getUser().getEmployeId().getId());
        HttpEntity<PushNotificationPayload> entity = new HttpEntity<PushNotificationPayload>(requestPayload,headers);
        String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/loadNotification", HttpMethod.POST, entity, String.class).getBody();

        if(responseAsStrign.equalsIgnoreCase("empty")){
            Map<String,Object> response = new HashMap<>();
            return response;
        }
        ObjectMapper obj = new ObjectMapper();
        Map<String,Object>  response = obj.readValue(responseAsStrign, HashMap.class);
        return response;
    }

    @Override
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
