package com.roms.api.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.dto.FileDto;
import com.roms.api.kafka.KafkaProducer;
import com.roms.api.model.*;
import com.roms.api.requestInput.SearchInput;
import com.roms.api.service.*;
import org.codehaus.plexus.util.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private EmployeeAddressService employeeAddressService;

    @Autowired
    private EmployeeEmergencyContactService employeeEmergencyContactService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private EmployeeLicenceService licenceService;

    @Autowired
    private EmployeeBankService employeeBankService;

    @Autowired
    private EmployeeSuperannuationService employeeSuperannuationService;

    @Autowired
    private EmployeeTFNService employeeTFNService;

    @Autowired
    private EmployeeMembershipService employeeMembershipService;


    @Autowired
    private EmployeeProfileImageService employeeProfileImageService;

    @RequestMapping(value = "/uploadpic" , method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE  })
    public ResponseEntity<Map<String,Object>> uploadFile(@RequestParam String id, @RequestParam(value="file") MultipartFile[] filse) throws IOException {

        Map<String, Object> response = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, MultipartFile> imageData = new HashMap();
        try {
            List<DigitalAssets> digitalAsset = new ArrayList<>();
            for (MultipartFile file : filse) {
                FileDto fileDto = FileDto.builder()
                        .file(file).build();
                DigitalAssets digitalAssets = minioService.uploadFile(fileDto);
                employeService.uploadPic(digitalAssets, id);

            }
            response.put("status", "success");

        } catch (Exception ee) {
            ee.printStackTrace();
            response.put("status", "error");
            response.put("error", ee.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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

    @GetMapping(value = "/personalinfo/{employeeId}")
    public ResponseEntity<?> loadPersonalInfo(@PathVariable("employeeId") String employeeId){
        Map<String, Object> response = new HashMap<>();
        Optional<Employe> requestedPage =  employeService.findByEmployeeId(employeeId);
        response.put("personal",requestedPage.get());
        List<EmployeeAddress> allAddress = new ArrayList<>();
        employeeAddressService.findAllByEmployeId(employeeId).forEach( obj ->{
            obj.setEmploye(null);
            allAddress.add(obj) ;
        });
        response.put("address",allAddress);
        List<EmployeeEmergencyContact> emergencyContact =  new ArrayList<>();
                 employeeEmergencyContactService.findEmergencyContactByEmployeeId(employeeId).forEach(
                obj->{
                    obj.setEmploye(null);
                    emergencyContact.add(obj);
                }
        );
        response.put("emergencyContact",emergencyContact);


        List<EmployeeLicence> licence = new ArrayList<>();
        licenceService.findLicenceByEmployeeId(employeeId).forEach(obj->{
            obj.setEmploye(null);
            licence.add(obj);
        });
        response.put("licence",licence);

        List<EmployeeBanks>  bankingDetails = new ArrayList<>();
        employeeBankService.findBankDetailsByEmployee(employeeId).forEach(obj->{
            obj.setEmploye(null);
            bankingDetails.add(obj);
        });
        response.put("bankingDetails",bankingDetails);


        List<EmployeeSuperannuation> superannuation = new ArrayList<>();
        employeeSuperannuationService.findSuperAnnuationByEmployeeId(employeeId).forEach(
                obj->{
                    obj.setEmploye(null);
                    superannuation.add(obj);
                }
        );
        response.put("superannuation",superannuation);

        List<EmployeeTFN> tfn = new ArrayList<>();
        employeeTFNService.findTFNbyEmployeeId(employeeId).forEach(obj->{
            obj.setEmploye(null);
            tfn.add(obj);
        });
        response.put("tfn",tfn);

        List<EmployeeMembership> membership = new ArrayList<>();
        employeeMembershipService.findMembershipByEmployeeId(employeeId).forEach(obj->{
            obj.setEmploye(null);
            membership.add(obj);
        });
        response.put("membership",membership);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }







    @GetMapping(value = "/load")
    public ResponseEntity<?> loadAllEmployee(
            @RequestParam(value ="page", defaultValue = "0") int page,
            @RequestParam(value ="size", defaultValue = "3") int size,
            @RequestParam(value ="empName", defaultValue = "") String empName){

        logger.info(("Process add new brand"));
        Map<String, Object> response = new HashMap<>();
        try {
           Page<Employe> requestedPage = null;
           if(StringUtils.isBlank(empName)){
               requestedPage = employeService.findAll(page,size);
           }else{
               requestedPage = employeService.findAllFilterByName(page,size,empName);
           }

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
