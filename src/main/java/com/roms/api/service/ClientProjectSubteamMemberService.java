package com.roms.api.service;
import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.repository.ClientProjectSubteamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientProjectSubteamMemberService {
    @Autowired
    private ClientProjectSubteamMemberRepository clientProjectSubteamMemberRepository;
    public void save(ClientProjectSubteamMember model){
        clientProjectSubteamMemberRepository.save(model);
    }
}
