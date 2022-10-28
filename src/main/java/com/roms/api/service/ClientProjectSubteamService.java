package com.roms.api.service;
import com.roms.api.model.*;
import com.roms.api.repository.ClientProjectSubteamRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class ClientProjectSubteamService {

    @Autowired
    private ClientProjectSubteamRepository clientProjectSubteamRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public ClientProjectSubteam save(ClientProjectSubteam model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        return clientProjectSubteamRepository.save(model);
    }

    public ClientProjectSubteam update(ClientProjectSubteam model){
        model.setLastUpdateDate(Instant.now());
        model.setUpdateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        return clientProjectSubteamRepository.save(model);
    }

    public List<ClientProjectSubteam> findAll(){
      return  clientProjectSubteamRepository.findAllByOrganisation(loggedIn.getOrg());
    }

    public List<ClientProjectSubteam> searChBySubTeamName(String searchText){
       // searchText = "%"+searchText+"%";
        return  clientProjectSubteamRepository.findAllByTeamNameContainsIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }

    public Optional<ClientProjectSubteam> findById(String Id){
       return clientProjectSubteamRepository.findById(Id);
    }


}
