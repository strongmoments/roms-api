package com.roms.api.service;

import com.roms.api.model.Client;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.repository.ClientRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public Client save(Client client){
        client.setCreateBy(loggedIn.getUser());
        client.setCreateDate(Instant.now());
        client.setOrganisation(loggedIn.getOrg());
        return  clientRepository.save(client);
    }


    public List<Client> searchByClientName(String searchText){

        return  clientRepository.findAllByNameContainingIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }

}
