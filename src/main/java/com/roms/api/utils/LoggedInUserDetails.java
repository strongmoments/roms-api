package com.roms.api.utils;

import com.roms.api.constant.Constant;
import com.roms.api.model.Organisation;
import com.roms.api.model.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoggedInUserDetails {

    public Users getUser(){
        Map<String,Object> loggedInUser = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return (Users) loggedInUser.get(Constant.USER_ID);
    }

    public Organisation getOrg(){
        Map<String,Object> loggedInUser = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
       return new Organisation((loggedInUser.get(Constant.ORG_ID).toString()));

    }
}
