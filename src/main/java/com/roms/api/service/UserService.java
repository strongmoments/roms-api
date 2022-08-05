package com.roms.api.service;


import com.roms.api.model.Roles;
import com.roms.api.model.Users;
import com.roms.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService  {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleService roleService;

     public Optional<Users> findByUsername(String username) {

        return usersRepository.findByUsername(username);
    }

    public Users save(Users model) {
        Optional<Roles> rolseDetails = roleService.findByRoleName(model.getRole().getRoleName());
        if(!rolseDetails.isEmpty()){
            model.setRole(rolseDetails.get());
            return usersRepository.save(model);
        }
        return model;

    }
}
