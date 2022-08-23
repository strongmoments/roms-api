package com.roms.api.controller;


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
           // workbook.getSheet()
            Map<String,String> loggedIndUser = (Map<String,String>)SecurityContextHolder.getContext().getAuthentication().getDetails();
            List<String> existingUserIds = userService.findAllUserIdByOrganisation(loggedIndUser.get("orgId").toString());
            List<Users> users = new ArrayList<>();
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            int counter =0;
            while (rows.hasNext()) {
                counter = counter+1;

                Row currentRow = rows.next();
                //currentRow
                    String emailId = currentRow.getCell(4) == null ? "" :currentRow.getCell(4).getStringCellValue();
                    if(existingUserIds.contains(emailId)){
                        continue;
                    }
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

                    if(counter ==2){
                        manageId = emailId;
                        roleName = "ROLE_ADMIN";
                    }

                    Users userModel = new Users();
                    Employe employeModel = new Employe();
                    Roles rolesModel = new Roles();

                    employeModel.setFirstName(firstName);
                    employeModel.setLastName(surName);
                    employeModel.setMiddleName(middleName);
                    employeModel.setPhone(phoneNumber);
                    employeModel.setEmail(emailId);
                    employeModel.setJobTitle(positionTitle);
                    employeModel.setGender(gender);
                    employeModel.setDepartmentIdx("2f67b643-18e1-11ed-861d-0242ac120002");
                    employeModel.setEmployeType(new EmployeType("374028ad-40c9-43c0-b5e5-907e21240a9e"));
                    employeModel.setIndigenousFlag(false);

                    rolesModel.setName(roleName.toUpperCase());

                    userModel.setDisableFlag(false);
                    userModel.setRole(rolesModel);
                    userModel.setEmployeId(employeModel);
                    userModel.setUserId(emailId);
                    userModel.setAuthenticatonType("JWT");
                    userModel.setApppassword(password);
                    users.add(userModel);

            }
            try {

                for(Users userModel : users){
                    //kafkaProducer.postUser("usermodel-rtl.kafka.data.save", "user", userModel);
                    userModel = userService.save(userModel);
                    List<ClientProjectSubteam> prjectTeams = clientProjectSubteamService.findAll();
                    if(prjectTeams.isEmpty()){
                        continue;
                    }
                    ClientProjectSubteamMember  teamMember = new ClientProjectSubteamMember();
                    teamMember.setClientProjectSubteam(prjectTeams.get(0));
                    teamMember.setEmployee(userModel.getEmployeId());
                    if(manageId.equalsIgnoreCase(userModel.getEmployeId().getEmail())){
                        teamMember.setManagerFlag(true);
                    }else{
                        teamMember.setManagerFlag(false);
                    }
                    clientProjectSubteamMemberService.save(teamMember);
                }

            } catch (Exception e) {
                logger.error("An error occurred! {}", e.getMessage());

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
}
