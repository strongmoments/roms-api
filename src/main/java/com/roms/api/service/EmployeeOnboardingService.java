package com.roms.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.requestInput.*;
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


    public void oboardEmergencyDetail(OnboardingEmergencyContactInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/emergencycontact";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingEmergencyContactInput> entity = new HttpEntity<OnboardingEmergencyContactInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }

    public void oboardLicence(OnboardingLicenceInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/licence";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingLicenceInput> entity = new HttpEntity<OnboardingLicenceInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }


    public void onboardBanking(OnboardingBankingInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/banking";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingBankingInput> entity = new HttpEntity<OnboardingBankingInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }

    public void onboardTFN(OnboardingTFNInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/tfn";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingTFNInput> entity = new HttpEntity<OnboardingTFNInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }

    public void onboardMembership(OnboardingMembershipInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/membership";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingMembershipInput> entity = new HttpEntity<OnboardingMembershipInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }

    public void onboardFeedback(OnboardingFeedBackInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/feedback";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingFeedBackInput> entity = new HttpEntity<OnboardingFeedBackInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }


    public void onboardsuperannuation(OnboardingSuperannuationInput paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/superannuation";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<OnboardingSuperannuationInput> entity = new HttpEntity<OnboardingSuperannuationInput>(paylod,headers);
            paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }

    public void onboarComplition(EmployeePayLoad paylod, Map<String,Object> responses ){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/v1/employee/onboard/complete";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<EmployeePayLoad> entity = new HttpEntity<EmployeePayLoad>(paylod,headers);
           // paylod.setId(logged.getUser().getEmployeId().getId());
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            responses.put("status","success");

        }catch (Exception e){
            responses.put("status","error");
            responses.put("error",e.getMessage());
        }

    }



    public String loadONboardedStatus(String onboardingType){
        List<Object> dataList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String employeeid = logged.getUser().getEmployeId().getId();

        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/v1/employee/onboard/"+onboardingType+"/"+employeeid, HttpMethod.GET, entity, String.class).getBody();



        return responseAsStrign;
    }


    public String loadAll(){
        List<Object> dataList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/v1/employee/onboard/allOnboardingStatus", HttpMethod.GET, entity, String.class).getBody();



        return responseAsStrign;
    }

    public String loadByEmployeId(){
        List<Object> dataList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String employeeid = logged.getUser().getEmployeId().getId();

        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/v1/employee/onboard/status/"+employeeid, HttpMethod.GET, entity, String.class).getBody();



        return responseAsStrign;
    }
}
