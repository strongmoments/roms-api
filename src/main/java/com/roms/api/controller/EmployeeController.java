package com.roms.api.controller;



import com.roms.api.kafka.KafkaProducer;
import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeDevices;
import com.roms.api.model.LeaveRequest;
import com.roms.api.requestInput.SearchInput;
import com.roms.api.service.ClientProjectSubteamMemberService;
import com.roms.api.service.EmployeService;
import com.roms.api.service.EmployeeDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/employee")

public class EmployeeController {

    @Autowired
    private KafkaProducer kafkaProducer;
    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Value("rtl")
    String kafkaGroupId;

    @Value("employee-rtl.kafka.data.save")
    String postBrandTopic;

    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;
    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeeDeviceService employeeDeviceService;

    @PostMapping(value = "/adddevice", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> requestLeave(@RequestBody EmployeeDevices employeeDevices) throws ParseException {
        Map<String,Object> response = new HashMap<>();
        try {

            employeeDeviceService.save(employeeDevices);

        }catch (Exception e){
            logger.error("Error while applying leave {} ",  e.getMessage());
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("status","success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "/load/{employeeId}")
    public ResponseEntity<?> loadAEmployeeById(@PathVariable("employeeId") String employeeId) throws ChangeSetPersister.NotFoundException {
            Map<String, Object> response = new HashMap<>();
        Optional<Employe> requestedPage =  employeService.findByEmployeeId(employeeId);
            if(requestedPage.isEmpty()){
                response.put("msg","record not found!");
               return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<>(requestedPage.get(), HttpStatus.OK);
    }



    @GetMapping(value = "/load")
    public ResponseEntity<?> loadAllEmployee(
            @RequestParam(value ="page", defaultValue = "0") int page,
            @RequestParam(value ="size", defaultValue = "3") int size){

        logger.info(("Process add new brand"));
        Map<String, Object> response = new HashMap<>();
        try {
           Page<Employe> requestedPage =  employeService.findAll(page,size);
            response.put("totalElement", requestedPage.getTotalElements());
            response.put("totalPage", requestedPage.getTotalPages());
            response.put("numberOfelement", requestedPage.getNumberOfElements());
            response.put("currentPageNmber", requestedPage.getNumber());
            response.put("data", requestedPage.getContent());
        } catch (Exception e){
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status","error");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
