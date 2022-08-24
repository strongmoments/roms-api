package com.roms.api.controller;


import com.roms.api.constant.Constant;
import com.roms.api.model.*;
import com.roms.api.service.ClientProjectSubteamMemberService;
import com.roms.api.service.ClientProjectSubteamService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.roms.api.kafka.KafkaProducer;
import com.roms.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/user")
@Secured("ROLE_ADMIN")
public class UserController {

    @Autowired
    private KafkaProducer kafkaProducer;
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("rtl")
    String kafkaGroupId;
    @Value("${common.password}")
    String password;

    @Value("user-rtl.kafka.data.save")
    String postBrandTopic;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientProjectSubteamService clientProjectSubteamService;

    @Autowired
    private ClientProjectSubteamMemberService  clientProjectSubteamMemberService;

    private Map<String,LocationType>  locationTypeMap;
    private Map<String,Location>  locationMap;


    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addLayeredBrand(@RequestBody Users userModel) {
        logger.info(("Process add new brand"));
        Map<String, Object> response = new HashMap<>();
        try {
            //kafkaProducer.postUser(postBrandTopic, kafkaGroupId, userModel);
            userService.save(userModel);
        } catch (Exception e) {
            logger.error("An error occurred! {}", e.getMessage());
            response.put("status", "error");
            response.put("error", e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Object>> submitInspection(@RequestParam(value = "file") MultipartFile filse) throws IOException {

        Map<String, Object> response = new HashMap();
        String manageId ="";
        try {
            Workbook workbook = new XSSFWorkbook(filse.getInputStream());

            Sheet sheet  = workbook.getSheetAt(0);
            Sheet locationType =  workbook.getSheet(Constant.LOCATION_TYPE);
            Map<String,String> loggedIndUser = (Map<String,String>)SecurityContextHolder.getContext().getAuthentication().getDetails();
            List<String> existingUserIds = userService.findAllUserIdByOrganisation(loggedIndUser.get("orgId").toString());
            List<Users> users = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            int counter =0;
            while (rows.hasNext()) {
                counter = counter+1;

                Row currentRow = rows.next();

                    String emailId = currentRow.getCell(4) == null ? "" :currentRow.getCell(4).getStringCellValue();
                    if(existingUserIds.contains(emailId)){
                        continue;
                    }
                //currentRow.cel
                    String surName = currentRow.getCell(0) == null ? "" :currentRow.getCell(0).getStringCellValue();
                    String firstName = currentRow.getCell(1) == null ? "" :currentRow.getCell(1).getStringCellValue();
                    String middleName = currentRow.getCell(2) == null ? "" :currentRow.getCell(2).getStringCellValue();
                    String phoneNumber = currentRow.getCell(3) == null ? "" : currentRow.getCell(3).getStringCellValue();

                    String positionTitle = currentRow.getCell(5) == null ? "" :currentRow.getCell(5).getStringCellValue();
                    //String dob = currentRow.getCell(6) == null ? "" :String.valueOf(currentRow.getCell(6).getNumericCellValue());
                    String gender = currentRow.getCell(7) == null ? "m" :currentRow.getCell(7).getStringCellValue().toLowerCase();
                    gender = gender.toUpperCase();

                    String roleName = currentRow.getCell(8) == null ? "ROLE_EMPLOYEE" :currentRow.getCell(8).getStringCellValue().toLowerCase();
                    roleName = roleName.toUpperCase();
                    roleName = roleName.trim();



            }

            workbook.close();
            response.put("status", "success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

       public  void SaveLoctionType(Sheet sheet){
         Map<String,Integer> headerIndex = new HashMap<>();
           Iterator<Row> rows = sheet.iterator();
           while (rows.hasNext()) {
               Row headerRow = rows.next();
               AtomicInteger counter = new AtomicInteger();
               headerRow.forEach(cell->{
                   headerIndex.put(cell.getStringCellValue(),counter.getAndIncrement());
;               });

           }
           int counter =0;
           while (rows.hasNext()) {
               Row currentRow = rows.next();
               String locationType = currentRow.getCell(headerIndex.get(Constant.LOCATION_TYPE)).getStringCellValue();


           }
       }

}
