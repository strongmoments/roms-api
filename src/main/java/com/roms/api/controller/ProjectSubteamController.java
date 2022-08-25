package com.roms.api.controller;

import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.requestInput.SearchInput;
import com.roms.api.service.ClientProjectSubteamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/projectsubteam")
public class ProjectSubteamController {

    @Autowired
    private ClientProjectSubteamService clientProjectSubteamService;

    @PostMapping(value = "/search")
    public ResponseEntity<?> searchProjectSubTeam(@RequestBody SearchInput employeeSearch) throws ChangeSetPersister.NotFoundException {
        List<ClientProjectSubteam> requestedPage =  clientProjectSubteamService.searChBySubTeamName(employeeSearch);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
