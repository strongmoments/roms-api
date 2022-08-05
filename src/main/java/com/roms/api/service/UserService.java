package com.roms.api.service;


import com.roms.api.model.Users;
import com.roms.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  {

    @Autowired
    private UsersRepository usersRepository;


     public Users findByUsername(String username) {

        return usersRepository.findByUsername(username);
    }

    public Users save(Users model) {
        return usersRepository.save(model);
    }
}
