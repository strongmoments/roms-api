package com.roms.api.service;


import com.roms.api.model.*;
import com.roms.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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



     public Optional<Users> findByUsername(String username,String orgId) {

        return usersRepository.findByUserIdAndOrganisation(username,getOrganisation(orgId));
    }

    public List<String> findAllUserIdByOrganisation(String orgId){
         return usersRepository.findAllUserIdByOrganisation(orgId);
    }

    public Users save(Users usersModel) {
         if(doesUserExist(usersModel.getUserId())){
             // @todo
             // need to throw user already exist
             return usersModel;
         }


        Map<String,Object> loggedInUserDetails =(Map<String,Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Users loggedInUser = (Users) loggedInUserDetails.get("userId");

        Optional<Roles> rolseDetails = roleService.findByRoleName(usersModel.getRole().getName());
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

        if(usersRepository.findByUserIdAndOrganisation(userId, getOrganisation(loggedInDetails.get("orgId"))).isEmpty()){
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
