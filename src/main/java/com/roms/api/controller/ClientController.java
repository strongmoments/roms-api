package com.roms.api.controller;

import com.roms.api.model.Client;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.EmployeeSkilsCirtificate;
import com.roms.api.repository.ClientRepository;
import com.roms.api.service.ClientService;
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
@RequestMapping(value = "/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping()
    public ResponseEntity<?> saveCirtificate(@RequestBody() Client client ){
        Map<String,Object> response = new HashMap();
        try {
            Client client1 =clientService.save(client);
            response.put("status","success");
            response.put("id",client1.getId());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("status", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchByCirtificateCode(@RequestParam(value ="name", defaultValue = "") String searchText) throws ChangeSetPersister.NotFoundException {
        List<Client> requestedPage =  clientService.searchByClientName(searchText);
        return new ResponseEntity<>(requestedPage, HttpStatus.OK);
    }
}
