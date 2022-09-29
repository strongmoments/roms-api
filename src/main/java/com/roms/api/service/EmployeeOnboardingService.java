package com.roms.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.requestInput.EmployeePayLoad;
import com.roms.api.requestInput.OnboardingPersonalDetailInput;
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
public class EmployeeOnboardingService {

    @Autowired
    private LoggedInUserDetails logged;


    public void oboardPersonalDetail(OnboardingPersonalDetailInput paylod,  Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/personal";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingPersonalDetailInput> entity = new HttpEntity<OnboardingPersonalDetailInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }

    public String loadONboardedStatus(){
        List<Object> dataList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String employeeid = logged.getUser().getEmployeId().getId();

        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/v1/employee/onboard/personal/"+employeeid, HttpMethod.GET, entity, String.class).getBody();



        return responseAsStrign;
    }
}
