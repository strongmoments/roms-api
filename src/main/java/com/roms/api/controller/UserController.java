package com.roms.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.config.CustomPasswordEncoder;
import com.roms.api.constant.Constant;
import com.roms.api.model.*;
import com.roms.api.service.*;
import com.roms.api.utils.LoggedInUserDetails;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.roms.api.kafka.KafkaProducer;
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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.random.RandomGenerator;

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
    private CustomPasswordEncoder customPasswordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    private LoggedInUserDetails loggedIn;
    @Autowired
    private ClientProjectSubteamService clientProjectSubteamService;
    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @Autowired
    private LocationTypeService locationTypeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ClientProjectService clientProjectService;

    @Autowired
    private EmployeTypeService employeTypeService;


    Map<String,LocationType> locationTypeMap;
    Map<String,Location> locationMap;
    Map<String,Departments> departmentsMap;
    Map<String,ClientProject> clientProjectMap;
    Map<String,ClientProjectSubteam> clientProjectSubteamMap;
    Map<String, EmployeType> employeTypeMap;




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

    @RequestMapping(value = "/uploadPic" , method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE  })
    public ResponseEntity<Map<String,Object>> uploadPic(@RequestParam(value="files") MultipartFile[] filse) throws IOException {

        Map<String, Object> response = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, MultipartFile> imageData = new HashMap();
        try {
            for (MultipartFile file : filse) {
                String fileName = file.getOriginalFilename();
                imageData.put(fileName, file);
            }
            imageData.forEach((k, v) -> {
                String emailId = "";
                int endIndex = k.lastIndexOf(".");
                if (endIndex != -1) {
                    emailId = k.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
                }
                Optional<Users> users = userService.findByUsername(emailId, loggedIn.getOrg().getId());
                if (!users.isEmpty()) {
                    try {

                        userService.uploadProfilePic(users.get().getEmployeId(), v);
                        response.put(emailId, "updated");

                    } catch (IOException e) {
                        e.printStackTrace();
                        response.put("error", e.getMessage());
                    }
                }
            });
        } catch(Exception ee){
            ee.printStackTrace();
            response.put("error", ee.getMessage());
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Object>> submitInspection(@RequestParam(value = "file") MultipartFile filse) throws IOException {
        Map<String, Object> response = new HashMap();
        String manageId = "mmoroney@rtl.com.au";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Workbook workbook = new XSSFWorkbook(filse.getInputStream());
            Sheet locationTypeSheet = workbook.getSheet(Constant.LOCATION_TYPE);
            Sheet locationSheet  = workbook.getSheet(Constant.LOCATION);
            Sheet employeeTypeSheet = workbook.getSheet(Constant.EMPLOYEE_TYPE);
            Sheet departmentSheet  = workbook.getSheet(Constant.DEPARTMENT);
            Sheet clientProjectSheet  = workbook.getSheet(Constant.CLIENT_PROJECT);
            Sheet clientProjectTeamSheet  = workbook.getSheet(Constant.CLIENT_PROJECT_TEAM);

            Sheet employeeSheet  = workbook.getSheet(Constant.EMPLOYEE);

            saveLoctionType(locationTypeSheet);
            saveLocaton(locationSheet);
            saveDepartment(departmentSheet);
            saveClientProject(clientProjectSheet);
            saveClientProjectTeam(clientProjectTeamSheet);
            saveEmployeType(employeeTypeSheet);
            saveEmplyee(employeeSheet, response);
            workbook.close();

        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    // save location type
    public  void saveLoctionType(Sheet sheet){
        locationTypeMap = new HashMap<>();
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);

        int counter =0;
        while (rows.hasNext()) {
            LocationType locationType = new LocationType();
            Row currentRow = rows.next();
            String desc = currentRow.getCell(headerIndex.get(Constant.LOCATION_TYPE)).getStringCellValue();
            String code = currentRow.getCell(headerIndex.get(Constant.LOCATION_TYPE_CODE)).getStringCellValue();
            locationType.setDescription(desc);
            locationType.setCode(code);
            locationType = locationTypeService.save(locationType);
            locationTypeMap.put(code,locationType);

        }
    }

    // save locations
    public  void saveLocaton(Sheet sheet){
        locationMap = new HashMap<>();
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);

        int counter =0;
        while (rows.hasNext()) {
            Location location = new Location();
            Row currentRow = rows.next();
            String desc = getCellStringValue(currentRow,headerIndex.get(Constant.DESC)) ;
            String code = getCellStringValue(currentRow,headerIndex.get(Constant.CODE));
            String address = getCellStringValue(currentRow,headerIndex.get(Constant.ADDRESS));
            String geoCode  =getCellStringValue(currentRow,headerIndex.get(Constant.GEO_CODE));
            String locationTypeCoe = getCellStringValue(currentRow,headerIndex.get(Constant.LOCATION_TYPE_CODE));

            location.setCode(code);
            location.setDescription(desc);
            location.setAddress(address);
            location.setGeoCdoe(geoCode);
            LocationType locationType = locationTypeMap.get(locationTypeCoe);
            location.setLocationType(locationType);
            location = locationService.save(location);

            locationMap.put(code,location);

        }
    }

    // save departments  type
    public  void saveDepartment(Sheet sheet){
        departmentsMap = new HashMap<>();
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);

        int counter =0;
        while (rows.hasNext()) {
            Departments departments = new Departments();
            Row currentRow = rows.next();
            String desc =  getCellStringValue(currentRow,headerIndex.get(Constant.DEPARTMENT_NAME));
            String code = getCellStringValue(currentRow,headerIndex.get(Constant.CODE));
            departments.setDescription(desc);
            departments.setCode(code);
            departments = departmentService.save(departments);
            departmentsMap.put(code,departments);

        }
    }


    // save client project
    public  void saveClientProject(Sheet sheet){
        clientProjectMap = new HashMap<>();
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);

        int counter =0;
        while (rows.hasNext()) {
            ClientProject clientProject = new ClientProject();
            Row currentRow = rows.next();
            String desc =  getCellStringValue(currentRow,headerIndex.get(Constant.PROJECT_NAME)); //currentRow.getCell(headerIndex.get(Constant.)).getStringCellValue();
            String code = getCellStringFromNumeric(currentRow,headerIndex.get(Constant.PROJECT_CODE));
            clientProject.setName(desc);
            clientProject  = clientProjectService.save(clientProject);
            clientProjectMap.put(code,clientProject);

        }
    }

    // save client subteam
    public  void saveClientProjectTeam(Sheet sheet){
        clientProjectSubteamMap = new HashMap<>();
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);

        int counter =0;
        while (rows.hasNext()) {
            ClientProjectSubteam clientProjectSubteam = new ClientProjectSubteam();
            Row currentRow = rows.next();
            String teamName = getCellStringValue(currentRow,headerIndex.get(Constant.TEAM_NAME)); // currentRow.getCell(headerIndex.get(Constant.PROJECT_NAME)).getStringCellValue();
            String code = getCellStringValue(currentRow,headerIndex.get(Constant.GANG_CODE)); // currentRow.getCell(headerIndex.get(Constant.GANG_CODE)).getStringCellValue();
            String locationCode = getCellStringValue(currentRow,headerIndex.get(Constant.LOCATION_CODE)); // currentRow.getCell(headerIndex.get(Constant.LOCATION_CODE)).getStringCellValue();
            String projectCode = getCellStringFromNumeric(currentRow,headerIndex.get(Constant.PROJECT_CODE)); // currentRow.getCell(headerIndex.get(Constant.PROJECT_CODE)).getStringCellValue();

            clientProjectSubteam.setCode(code);
            clientProjectSubteam.setTeamName(teamName);
            Location location = locationMap.get(locationCode);
            ClientProject clientProject = clientProjectMap.get(projectCode);
            clientProjectSubteam.setLocation(location);
            clientProjectSubteam.setClientProject(clientProject);
            clientProjectSubteam  = clientProjectSubteamService.save(clientProjectSubteam);
            clientProjectSubteamMap.put(code,clientProjectSubteam);

        }
    }

    // save employeeType
    public  void saveEmployeType(Sheet sheet){
        employeTypeMap = new HashMap<>();
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);

        int counter =0;
        while (rows.hasNext()) {
            EmployeType employeType = new EmployeType();
            Row currentRow = rows.next();
            String empType = currentRow.getCell(headerIndex.get(Constant.EMPLOYEE_TYPE)).getStringCellValue();
            String code = currentRow.getCell(headerIndex.get(Constant.EMPLOYEE_TYPE_CODE)).getStringCellValue();
            employeType.setName(empType);
            employeType = employeTypeService.save(employeType);
            employeTypeMap.put(code,employeType);

        }
    }

    // save employee
    public  void saveEmplyee(Sheet sheet,  Map<String, Object> response ){
        Iterator<Row> rows = sheet.iterator();
        Row headerRow = rows.next();
        Map<String,Integer> headerIndex = getHeaderIndex(headerRow);
        Random random = new Random();

        Map<String, String> loggedIndUser = (Map<String, String>) SecurityContextHolder.getContext().getAuthentication().getDetails();

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                String surName =   getCellStringValue(currentRow, headerIndex.get(Constant.SURNAME));  //currentRow.getCell(0) == null ? "" : currentRow.getCell(0).getStringCellValue();
                String firstName = getCellStringValue(currentRow, headerIndex.get(Constant.FIRST_NAME));
                String middleName = getCellStringValue(currentRow, headerIndex.get(Constant.MIDDLE_NAME));
                String phoneNumber = getCellStringValue(currentRow, headerIndex.get(Constant.PHONE_NO));
                String positionTitle = getCellStringValue(currentRow, headerIndex.get(Constant.JOB_TITLE));
                Date dob = getCellDateValue(currentRow, headerIndex.get(Constant.DOB));
                String gender = getCellStringValue(currentRow, headerIndex.get(Constant.GENDER));
                gender = gender.toUpperCase();
                String roleName = getCellStringValue(currentRow, headerIndex.get(Constant.ROLE));
                roleName = roleName.toUpperCase();
                roleName = roleName.trim();

                String email = getCellStringValue(currentRow, headerIndex.get(Constant.EMAIL));
                email = email.trim();
                String employeeNO = getCellStringValue(currentRow, headerIndex.get(Constant.EMPLOYEE_NO));

                String departmentCode = getCellStringValue(currentRow, headerIndex.get(Constant.DEPARTMENT_CODE));
                String employeTypeCode = getCellStringValue(currentRow, headerIndex.get(Constant.EMPLOYEE_TYPE_CODE));
                String gangCode = getCellStringValue(currentRow, headerIndex.get(Constant.GANG_CODE));
                String isManager = getCellStringValue(currentRow, headerIndex.get(Constant.IS_MANAGER));



                Employe employeModel = new Employe();

                employeModel.setFirstName(firstName);
                employeModel.setEmployeeNo(employeeNO);
                employeModel.setLastName(surName);
                employeModel.setMiddleName(middleName);
                employeModel.setPhone(phoneNumber);
                employeModel.setEmail(email);
                employeModel.setJobTitle(positionTitle);
                employeModel.setBirthdate(dob.toInstant());
                employeModel.setGender(gender);
                employeModel.setDepartments(departmentsMap.get(departmentCode));
                employeModel.setEmployeType(employeTypeMap.get(employeTypeCode));
                employeModel.setIndigenousFlag(false);



                Users userModel = new Users();

                Roles rolesModel = new Roles();
                rolesModel.setName(roleName.toUpperCase());
                userModel.setDisableFlag(false);
                userModel.setRole(rolesModel);
                userModel.setEmployeId(employeModel);
                userModel.setUserId(email);
                userModel.setAuthenticatonType("JWT");
                String passwords  = "roms@"+random.nextInt(100,1000);
                userModel.setApppassword(customPasswordEncoder.encode(passwords));

                userModel = userService.save(userModel);


                ClientProjectSubteamMember teamMember = new ClientProjectSubteamMember();
                employeModel = userModel.getEmployeId();
                if("".equalsIgnoreCase(employeModel.getId()) || null == employeModel.getId()){
                    userModel =userService.findByUsername(userModel.getUsername(), loggedIn.getOrg().getId()).get();
                }

                teamMember.setEmployee(userModel.getEmployeId());

                if ("y".equalsIgnoreCase(isManager)) {
                    teamMember.setManagerFlag(true);
                } else {
                    teamMember.setManagerFlag(false);
                }
                teamMember.setClientProjectSubteam(clientProjectSubteamMap.get(gangCode));
                teamMember.setStartDate(Instant.now());
                clientProjectSubteamMemberService.save(teamMember);
                if(userModel.getId() != null){
                    response.put(email,passwords);
                }



            }
    }





    public Date getCellDateValue(Row row , Integer headerIndx){
        return row.getCell(headerIndx) == null ? new Date() : row.getCell(headerIndx).getDateCellValue();
    }
    public String getCellStringValue(Row row , Integer headerIndx){
        return row.getCell(headerIndx) == null ? "" : row.getCell(headerIndx).getStringCellValue();
    }
    public String getCellStringFromNumeric(Row row , Integer headerIndx){
         double val = row.getCell(headerIndx) == null ? 0 : row.getCell(headerIndx).getNumericCellValue();
        return String.valueOf(val);
    }

    public Map<String,Integer> getHeaderIndex(Row headerRow){
        Map<String,Integer> headerIndex = new HashMap<>();

        AtomicInteger headerCounter = new AtomicInteger();
        headerRow.forEach(cell->{
            headerIndex.put(cell.getStringCellValue(),headerCounter.getAndIncrement());
            ;               });
        return headerIndex;

    }

}