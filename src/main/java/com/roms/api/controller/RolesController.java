package com.roms.api.controller;

import com.roms.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/roles")
public class RolesController {

    @Autowired
    private RoleService roleService;
    @GetMapping(value = "/load")
    public ResponseEntity<?> loadRoles() {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data",roleService.findAllRoles());
        } catch (Exception e){

            response.put("status","error");
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
