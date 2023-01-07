package com.roms.api.controller;

import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.service.ClientProjectSubteamMemberService;
import com.roms.api.service.EmployeeManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/subteammember")
public class ProjectSubteamMemberController {

    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @Autowired
    private EmployeeManagerService employeeManagerService;

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchProjectSubTeam(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<ClientProjectSubteamMember> requestedPage =  clientProjectSubteamMemberService.findAllEmployeeByFirstNameOrNumber(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<?> employeeGangDetails(@PathVariable("employeeId") String employeeId) throws ChangeSetPersister.NotFoundException {
        Optional<ClientProjectSubteam> requestedPage =  clientProjectSubteamMemberService.findClientProjectSubTeamByEmployeeId(employeeId);
        Map<String, Object> response = new HashMap();
        if(requestedPage.isEmpty()){
            response.put("gangDetails", null);
        }else{
            response.put("gangDetails",requestedPage.get());
        }
        Optional<EmployeeManagers>  managerModel =  employeeManagerService.getManager(employeeId);
        if(managerModel.isPresent()){
            response.put("manager",managerModel.get().getManagers());
        }else{
            response.put("manager", null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
