package com.roms.api.service;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roms.api.model.*;
import com.roms.api.repository.UsersRepository;
import com.roms.api.requestInput.EmployeePayLoad;
import com.roms.api.utils.LoggedInUserDetails;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
public class UserService  {


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private  EmployeService employeService;

    @Autowired
    private UserRolesMapService userRolesMapService;

    @Autowired
    @Qualifier("addusernotification")
    private NotificationService notificationService;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public Optional<Users> findByUserId() {
        return usersRepository.findById(loggedIn.getUser().getId());
    }


     public Optional<Users> findByUsername(String username,String orgId) {

        return usersRepository.findByUserIdEqualsIgnoreCaseAndOrganisation(username,getOrganisation(orgId));
    }

    public List<String> findAllUserIdByOrganisation(String orgId){
         return usersRepository.findAllUserIdByOrganisation(orgId);
    }

    public void uploadProfilePic(Employe employe, MultipartFile file) throws IOException {

       // employe.setProfileImage(file.getBytes());
        employeService.save(employe);
    }
    public Users updateLastLogin(Users usersModel) {
        return usersRepository.save(usersModel);
    }

    public Users updateUser(Users usersModel) {
        usersModel.setUpdateBy(loggedIn.getUser());
        usersModel.setLastUpdateDate(Instant.now());
        return usersRepository.save(usersModel);
    }
    public List<Object> loadPendinRegistration() throws JsonProcessingException {

        List<Object> dataList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

      EmployeePayLoad  employeePayLoad = EmployeePayLoad.builder().orgId("ab905406-79a3-4e54-8244-d79fc0e60937").build();


        HttpEntity<EmployeePayLoad> entity = new HttpEntity<EmployeePayLoad>(employeePayLoad,headers);
        String responseAsStrign =  restTemplate.exchange(                "http://localhost:8081/loadUser", HttpMethod.POST, entity, String.class).getBody();

        if(!responseAsStrign.equalsIgnoreCase("empty")){
            ObjectMapper obj = new ObjectMapper();
            Map<String,Object>  response = obj.readValue(responseAsStrign, HashMap.class);
            response.forEach((k,v)->{

                dataList.add(v);
            });
        }

        return dataList;
    }

    public String saveTemporary(EmployeePayLoad employeePayLoad) throws InterruptedException {
            RestTemplate restTemplate = new RestTemplate();
            String URL  = "http://localhost:8081/addUser";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            UUID uuid=UUID.randomUUID();
            employeePayLoad.setId(String.valueOf(uuid));
            employeePayLoad.setAppliedOn(String.valueOf(Instant.now().toEpochMilli()));
            employeePayLoad.setStatus(1);
            HttpEntity<EmployeePayLoad> entity = new HttpEntity<EmployeePayLoad>(employeePayLoad,headers);
            String response = restTemplate.exchange(
                    URL, HttpMethod.POST, entity, String.class).getBody();
            if("success".equalsIgnoreCase(response)){
                notificationService.sendNotification(employeePayLoad);

            }
            return response;
    }
    public String updateTemporary(EmployeePayLoad employeePayLoad) {
        RestTemplate restTemplate = new RestTemplate();
        String URL  = "http://localhost:8081/updateUser";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        employeePayLoad.setId(loggedIn.getUser().getEmployeId().getId());
        employeePayLoad.setRegistrationDate(String.valueOf(Instant.now().toEpochMilli()));
        employeePayLoad.setStatus(2);
        employeePayLoad.setOrgId(loggedIn.getOrg().getId());
        HttpEntity<EmployeePayLoad> entity = new HttpEntity<EmployeePayLoad>(employeePayLoad,headers);
        String response = restTemplate.exchange(
                URL, HttpMethod.POST, entity, String.class).getBody();
        return response;
    }


    public Users save(Users usersModel) {
         if(doesUserExist(usersModel.getUserId())){
             // @todo
             // need to throw user already exist
             return usersModel;
         }


        Map<String,Object> loggedInUserDetails =(Map<String,Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Users loggedInUser = (Users) loggedInUserDetails.get("userId");
        Optional<Roles> rolseDetails = null;
        if(StringUtils.isNotBlank(usersModel.getRole().getId())){
            rolseDetails = roleService.findById(usersModel.getRole().getId());

        }else {
            rolseDetails = roleService.findByRoleName(usersModel.getRole().getName());
        }

        if(!rolseDetails.isEmpty()){
            Employe employeModel = usersModel.getEmployeId();
            employeModel.setCreateDate(Instant.now());
            employeModel.setOrganisation(employeModel.getOrganisation());
            employeModel.setCreateBy(loggedInUser);
            employeModel.setStartDate(Instant.now());
            employeModel = employeService.save(employeModel);

            usersModel.setEmployeId(employeModel);
            usersModel.setRole(rolseDetails.get());
            usersModel.setOrganisation(usersModel.getOrganisation());
            usersModel.setRole(rolseDetails.get());
            usersModel.setCreateDate(Instant.now());
            usersModel.setCreateBy(loggedInUser);
            usersModel =  usersRepository.save(usersModel);

            // making user and role mapping
            UserRolesMap userRolesMap = new UserRolesMap();
            userRolesMap.setUserId(usersModel);
            userRolesMap.setRoleId(rolseDetails.get());


            userRolesMap.setCreateBy(loggedInUser);
            userRolesMap.setUpdateBy(loggedInUser);
            userRolesMap.setCreateDate(Instant.now());
            userRolesMap.setOrganisation(userRolesMap.getOrganisation());
            userRolesMapService.save(userRolesMap);
            return usersModel;
        }else{
            //@todo
            // need to throw role does not exist
        }
        return usersModel;

    }

    public boolean doesUserExist(String userId){
        Map<String,String> loggedInDetails  = (Map<String,String>)SecurityContextHolder.getContext().getAuthentication().getDetails();

        if(usersRepository.findByUserIdEqualsIgnoreCaseAndOrganisation(userId, getOrganisation(loggedInDetails.get("orgId"))).isEmpty()){
           return false;
        }else{
            return  true;
        }
    }

    public boolean doesUserExist(String userId,String orgId){
        if(usersRepository.findByUserIdEqualsIgnoreCaseAndOrganisation(userId, getOrganisation(orgId)).isEmpty()){
            return false;
        }else{
            return  true;
        }
    }



    private  Organisation getOrganisation(String orgId){
        Organisation organisation = new  Organisation();
        organisation.setId(orgId);
        return organisation;
    }
}
