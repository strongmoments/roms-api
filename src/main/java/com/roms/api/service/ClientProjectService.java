package com.roms.api.service;

import com.roms.api.model.Client;
import com.roms.api.model.ClientContract;
import com.roms.api.model.ClientProject;
import com.roms.api.model.ClientProjectRole;
import com.roms.api.repository.ClientProjectRepository;
import com.roms.api.repository.ClientProjectRoleRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ClientProjectService {
    @Autowired
    private ClientProjectRepository clientProjectRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;


    public ClientProject save(ClientProject model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return clientProjectRepository.save(model);
    }

    public List<ClientProject> searchByContractName(String searchText, String clientId, String contractId){

        Client client = new Client();
        client.setId(clientId);
        ClientContract  contract = new ClientContract(contractId);


        return  clientProjectRepository.findAllByNameContainingIgnoreCaseAndOrganisationAndClientAndContract(searchText,loggedIn.getOrg(), client,contract);
    }


}
