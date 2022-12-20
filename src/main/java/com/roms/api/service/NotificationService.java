package com.roms.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.*;
import com.roms.api.requestInput.EmployeePayLoad;
import com.roms.api.requestInput.OnboardingCompleteInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;

@Service
public abstract class NotificationService {

    @Autowired
    private LoggedInUserDetails loggedIn;

    public  void sendNotification(String  eventId){}
    public  void sendNotification(EmployeePayLoad employeePayLoad) throws InterruptedException {}

    public  void sendNotification(EmployeeResourcedemand resourcedemand,String startDate) throws InterruptedException {}
    public  void sendNotification(OnboardingCompleteInput employeePayLoad) throws InterruptedException {}

    public  String sendsms(String toNumber ,String message)  {return "";}

    public void sendRecomendNotification(EmploymentRecommendation recomendNotifcation)throws InterruptedException {}

    public void sendRecomendationApproveOrRejectNotification(EmploymentRecommendation recomendNotifcation,String status)throws InterruptedException {}





    public  void sendApprovedOrRejectNotification(String eventId,String message,String type){}

    public Map<String,Object>  loadNotification() throws JsonProcessingException {
        return null;
    }



    public boolean deleteNotification(String eventId) throws JsonProcessingException {

        return false;
    }
}
