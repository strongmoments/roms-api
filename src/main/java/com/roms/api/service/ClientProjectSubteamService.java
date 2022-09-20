package com.roms.api.service;
import com.roms.api.model.*;
import com.roms.api.repository.ClientProjectSubteamRepository;
import com.roms.api.requestInput.SearchInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientProjectSubteamService {

    @Autowired
    private ClientProjectSubteamRepository clientProjectSubteamRepository;
    @Autowired
    private LoggedInUserDetails loggedIn;

    public ClientProjectSubteam save(ClientProjectSubteam model){
       return clientProjectSubteamRepository.save(model);
    }

    public List<ClientProjectSubteam> findAll(){
      return  clientProjectSubteamRepository.findAllByOrganisation(loggedIn.getOrg());
    }

    public List<ClientProjectSubteam> searChBySubTeamName(String searchText){
       // searchText = "%"+searchText+"%";
        return  clientProjectSubteamRepository.findAllByTeamNameContainsIgnoreCaseAndOrganisation(searchText,loggedIn.getOrg());
    }


}
