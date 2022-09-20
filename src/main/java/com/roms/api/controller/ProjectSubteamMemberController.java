package com.roms.api.controller;

import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.service.ClientProjectSubteamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/subteammember")
public class ProjectSubteamMemberController {

    @Autowired
    private ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchProjectSubTeam(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<ClientProjectSubteamMember> requestedPage =  clientProjectSubteamMemberService.findAllEmployeeByFirstNameOrNumber(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
