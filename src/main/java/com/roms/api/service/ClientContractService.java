package com.roms.api.service;

import com.roms.api.model.Client;
import com.roms.api.model.ClientContract;
import com.roms.api.repository.ClientContractRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ClientContractService {

    @Autowired
    private ClientContractRepository clientContractRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;


    public ClientContract save(ClientContract clientContract){
        clientContract.setCreateBy(loggedIn.getUser());
        clientContract.setCreateDate(Instant.now());
        clientContract.setOrganisation(loggedIn.getOrg());
        return  clientContractRepository.save(clientContract);
    }


    public List<ClientContract> searchByContractName(String searchText, String clientId){

        Client client = new Client();
        client.setId(clientId);


        return  clientContractRepository.findAllByNameContainingIgnoreCaseAndOrganisationAndClient(searchText,loggedIn.getOrg(), client);
    }


}
