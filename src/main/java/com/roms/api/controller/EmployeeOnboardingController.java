package com.roms.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.*;
import com.roms.api.requestInput.*;
import com.roms.api.service.*;
import com.roms.api.utils.LoggedInUserDetails;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employee/onboard")
public class EmployeeOnboardingController {
    public static final Logger logger = LoggerFactory.getLogger(EmployeeOnboardingController.class);

    @Autowired
    private EmployeeOnboardingService employeeOnboardingService;

    @Autowired
    private EmployeeBankService employeeBankService;

    @Autowired
    private  EmployeeTFNService employeeTFNService;

    @Autowired
    private EmployeeSuperannuationService employeeSuperannuationService;
    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeeEmergencyContactService employeeEmergencyContactService;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    @Qualifier("onboardingnotification")
    private NotificationService notificationService;



    @PostMapping(value = "/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> complte(@RequestBody() OnboardingCompleteInput request) {
        logger.info("cmplete: {}",request);
        Map<String, Object> response = new HashMap();
        try{
            response.put("status","success");
            request.setId(loggedIn.getUser().getEmployeId().getId());
            EmployeePayLoad employeePayLoad = EmployeePayLoad.builder()
                    .id(loggedIn.getUser().getEmployeId().getId()).build();
            employeeOnboardingService.onboarComplition(employeePayLoad, response);
            notificationService.sendNotification(request);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/superannuation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> superannuation(@RequestBody() OnboardingSuperannuationInput onboardingSuperannuationInput) {
        logger.info("superannuation: {}",onboardingSuperannuationInput);
        Map<String, Object> response = new HashMap();
        try{
            response.put("status","success");
            EmployeeSuperannuation employeeSuperannuation = employeeSuperannuationService.saveFromOnboarding(onboardingSuperannuationInput);
            if(employeeSuperannuation != null && employeeSuperannuation.getId() != null){
                employeeOnboardingService.onboardsuperannuation(onboardingSuperannuationInput, response);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/membership", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> membership(@RequestBody() OnboardingMembershipInput personalDetail) {
        logger.info("membership: {}",personalDetail);
        Map<String, Object> response = new HashMap();

        response.put("status","success");
          employeeOnboardingService.onboardMembership(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/feedback", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> feedback(@RequestBody() OnboardingFeedBackInput personalDetail) {
        logger.info("feedback: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        response.put("status","success");
        employeeOnboardingService.onboardFeedback(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/tfn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> TFN(@RequestBody() OnboardingTFNInput onboardingTFNInput) {
        logger.info("tfn: {}",onboardingTFNInput);
        Map<String, Object> response = new HashMap();
        try{
            response.put("status","success");
            EmployeeTFN employeeTFN = employeeTFNService.saveFromOnboarding(onboardingTFNInput);
            if(employeeTFN != null && employeeTFN.getId() != null){
                employeeOnboardingService.onboardTFN(onboardingTFNInput, response);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/banking", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> banking(@RequestBody() OnboardingBankingInput onboardingBankingInput) {
        logger.info("banking: {}",onboardingBankingInput);
        Map<String, Object> response = new HashMap();
        try{
            response.put("status","success");
            EmployeeBanks employeeBanks = employeeBankService.saveFromOnboarding(onboardingBankingInput);
            if(employeeBanks != null && employeeBanks.getId() != null){
                employeeOnboardingService.onboardBanking(onboardingBankingInput, response);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/licence", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody() OnboardingLicenceInput personalDetail) {
        logger.info("licence: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        response.put("status","success");
        employeeOnboardingService.oboardLicence(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/personal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody() OnboardingPersonalDetailInput personalDetail) {
        logger.info("personal: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        try {
                Employe employe = employeService.update(personalDetail);
                if(employe != null && employe.getId() != null){
                    // send notification
                }
                response.put("status","success");
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/emergency", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody() OnboardingEmergencyContactInput onboardingEmergencyContactInput) {
        logger.info("emergency: {}",onboardingEmergencyContactInput);
        Map<String, Object> response = new HashMap();
        try{
            response.put("status","success");
            EmployeeEmergencyContact emergencyContact =employeeEmergencyContactService.saveFromOnboarding(onboardingEmergencyContactInput);
            if(emergencyContact != null && emergencyContact.getId() != null){
                employeeOnboardingService.oboardEmergencyDetail(onboardingEmergencyContactInput, response);
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/personal/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> next(@RequestBody() OnboardingPersonalDetailInput personalDetail) {
        logger.info("personal/next: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.oboardPersonalDetail(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/emergency/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> next(@RequestBody() OnboardingEmergencyContactInput personalDetail) {
        logger.info("emergency/next: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.oboardEmergencyDetail(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/licence/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> next(@RequestBody() OnboardingLicenceInput personalDetail) {
        logger.info("licence/next: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.oboardLicence(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/banking/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> next(@RequestBody() OnboardingBankingInput personalDetail) {
        logger.info("banking/next: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.onboardBanking(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/tfn/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> tfnNext(@RequestBody() OnboardingTFNInput personalDetail) {
        logger.info("tfn/next: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.onboardTFN(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/superannuation/next", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> tfnNext(@RequestBody() OnboardingSuperannuationInput personalDetail) {
        logger.info("superannuation/next: {}",personalDetail);
        Map<String, Object> response = new HashMap();
        employeeOnboardingService.onboardsuperannuation(personalDetail, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping(value = "/personal/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadPersonal() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("personal");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/emergency/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> emergency() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("emergency");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/licence/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadLicence() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("licence");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/banking/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadBanking() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("banking");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/tfn/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadTFN() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("tfn");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/superannuation/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadsuperannuation() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("superannuation");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/membership/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadmembership() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("membership");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/feedback/load", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadfeedback() {
        Map<String, Object> response = new HashMap();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadONboardedStatus("feedback");
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){
                response.put("status","error");
                response.put("error","not_found");

            }else{
                response.put("status","success");
                response.put("data",obj.readValue(responses, HashMap.class));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getstatus() {
        Map<String, Object> response = new HashMap();
        Map<String,Object> responseobj = new HashMap<>();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadByEmployeId();
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){

                response.put("status","error");
                response.put("error","not_found");

            }else{
                Map<String,Object> object = obj.readValue(responses, HashMap.class);

                  //  Map<String, Object> onboardingDatata  = (Map<String, Object>) object.get(key);
                    for(Object key1 : object.keySet()){
                        String keyasString = (String) key1 ;
                        if(keyasString.equalsIgnoreCase("registrationDate") || keyasString.equalsIgnoreCase("endDate") || keyasString.equalsIgnoreCase("startdDate") || keyasString.equalsIgnoreCase("firstName") || keyasString.equalsIgnoreCase("lastName") || keyasString.equalsIgnoreCase("id") || keyasString.equalsIgnoreCase("empNo")){
                            continue;
                        }

                        Map<String, Object>  value   =  (Map<String, Object>) object.get(key1);

                        responseobj.put((String) key1,value.get("completionProgress"));

                }
                response.put("status","success");
                response.put("data",responseobj);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/loadAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadAll() {
        Map<String, Object> response = new HashMap();
        List<Object> objList = new ArrayList<>();
        try {
            ObjectMapper obj = new ObjectMapper();
            String responses =   employeeOnboardingService.loadAll();
            if(StringUtils.isBlank(responses) || "null".equalsIgnoreCase(responses)){

                response.put("status","error");
                response.put("error","not_found");

            }else{
                Map<String,Object> object = obj.readValue(responses, HashMap.class);
                for(Object key : object.keySet()){
                    objList.add(object.get(key));
                }
                response.put("status","success");
                response.put("data",objList);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
