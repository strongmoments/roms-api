package com.roms.api.service;
import com.roms.api.constant.Constant;
import com.roms.api.model.ClientProjectSubteam;
import com.roms.api.model.ClientProjectSubteamMember;
import com.roms.api.model.Organisation;
import com.roms.api.model.Users;
import com.roms.api.repository.ClientProjectSubteamMemberRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class ClientProjectSubteamMemberService {
    @Autowired
    private ClientProjectSubteamMemberRepository clientProjectSubteamMemberRepository;
   @Autowired
   private LoggedInUserDetails loggedIn;


    public void save(ClientProjectSubteamMember model){
        clientProjectSubteamMemberRepository.save(model);
    }

    public Optional<Users> findClientProjectSubTeamManager(String subTeamId){


       Optional<ClientProjectSubteamMember>  subteamMember = clientProjectSubteamMemberRepository.findByClientProjectSubteamAndManagerFlagAndOrganisation(new ClientProjectSubteam(subTeamId),true,loggedIn.getOrg());
       if(!subteamMember.isEmpty()){
          return Optional.ofNullable(subteamMember.get().getUser());

       }else{
           return  Optional.ofNullable(null);
       }

    }
}
