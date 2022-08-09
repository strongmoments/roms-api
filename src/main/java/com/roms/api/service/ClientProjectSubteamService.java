package com.roms.api.service;
import com.roms.api.model.*;
import com.roms.api.repository.ClientProjectSubteamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientProjectSubteamService {

    @Autowired
    private ClientProjectSubteamRepository clientProjectSubteamRepository;

    public void save(ClientProjectSubteam model){
       clientProjectSubteamRepository.save(model);
    }



}
