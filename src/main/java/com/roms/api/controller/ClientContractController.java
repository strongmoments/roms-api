package com.roms.api.controller;

import com.roms.api.model.Client;
import com.roms.api.model.ClientContract;
import com.roms.api.service.ClientContractService;
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
@RequestMapping(value = "/v1/client/contract")
public class ClientContractController {

    @Autowired
    private ClientContractService clientContractService;

    @PostMapping()
    public ResponseEntity<?> saveCirtificate(@RequestBody() ClientContract clientContract ){
        Map<String,Object> response = new HashMap();
        try {
            ClientContract clientContract1 =clientContractService.save(clientContract);
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
        List<ClientContract> requestedPage =  clientContractService.searchByContractName(searchText,clientId);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
