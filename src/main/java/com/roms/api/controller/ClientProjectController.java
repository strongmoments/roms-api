package com.roms.api.controller;

import com.roms.api.model.ClientContract;
import com.roms.api.model.ClientProject;
import com.roms.api.service.ClientProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/v1/client/project")
public class ClientProjectController {

    @Autowired
    private ClientProjectService clientProjectService;


    @PostMapping()
    public ResponseEntity<?> saveCirtificate(@RequestBody() ClientProject clientProject ){
        Map<String,Object> response = new HashMap();
        try {
            ClientProject clientContract1 =clientProjectService.save(clientProject);
            response.put("status","success");
            response.put("id",clientContract1.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search/{clientId}")
    public ResponseEntity<?> searchByCirtificateCode(@PathVariable("clientId") String clientId, @RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<ClientProject> requestedPage =  clientProjectService.searchByContractName(searchText,clientId);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
