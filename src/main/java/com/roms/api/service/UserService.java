package com.roms.api.service;


import com.roms.api.model.Employe;
import com.roms.api.model.Roles;
import com.roms.api.model.UserRolesMap;
import com.roms.api.model.Users;
import com.roms.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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


     public Optional<Users> findByUsername(String username) {

        return usersRepository.findByUserId(username);
    }

    public Users save(Users usersModel) {
         if(doesUserExist(usersModel.getUserId())){
             // @todo
             // need to throw user already exist
             return usersModel;
         }

        Optional<Roles> rolseDetails = roleService.findByRoleName(usersModel.getRole().getName());
        if(!rolseDetails.isEmpty()){
            Employe employeModel = usersModel.getEmployeId();
            employeModel.setCreateDate(Instant.now());
            employeModel = employeService.save(employeModel);

            usersModel.setEmployeId(employeModel);


            usersModel =  usersRepository.save(usersModel);

            // making user and role mapping
            UserRolesMap userRolesMap = new UserRolesMap();
            userRolesMap.setUserId(usersModel);
            userRolesMap.setRoleId(rolseDetails.get());
            userRolesMap.setUpdateBy(null);
            //@todo
            //userRolesMap.setCreateBy();
            userRolesMapService.save(userRolesMap);
            return usersModel;
        }else{
            //@todo
            // need to throw role does not exist
        }
        return usersModel;

    }

    public boolean doesUserExist(String userId){
        if(usersRepository.findByUserId(userId).isEmpty()){
           return false;
        }else{
            return  true;
        }
    }
}
