package com.roms.api.service;
import com.roms.api.constant.Constant;
import com.roms.api.model.*;
import com.roms.api.repository.ClientProjectSubteamMemberRepository;
import com.roms.api.requestInput.EmployeeSearch;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ClientProjectSubteamMemberService {
    @Autowired
    private ClientProjectSubteamMemberRepository clientProjectSubteamMemberRepository;
   @Autowired
   private LoggedInUserDetails loggedIn;


    public void save(ClientProjectSubteamMember model){
        model.setOrganisation(loggedIn.getOrg());
        model.setCreateDate(Instant.now());
        model.setCreateBy(loggedIn.getUser());
        clientProjectSubteamMemberRepository.save(model);
    }

    public Optional<ClientProjectSubteam> findClientProjectSubTeamByEmployeeId(String employeeId){
        List<ClientProjectSubteamMember> clientProjectSubteamMember = clientProjectSubteamMemberRepository.findByEmployeeAndOrganisation(new Employe(employeeId), loggedIn.getOrg());
        if(!clientProjectSubteamMember.isEmpty()){
            return Optional.ofNullable(clientProjectSubteamMember.get(0).getClientProjectSubteam());
        }else{
            return Optional.ofNullable(null);
        }

    }

    public Optional<Employe> findClientProjectSubTeamManager(String subTeamId){
       Optional<ClientProjectSubteamMember>  subteamMember = clientProjectSubteamMemberRepository.findByClientProjectSubteamAndManagerFlagAndOrganisation(new ClientProjectSubteam(subTeamId),true,loggedIn.getOrg());
       if(!subteamMember.isEmpty()){
          return Optional.ofNullable(subteamMember.get().getEmployee());

       }else{
           return  Optional.ofNullable(null);
       }

    }

    public Optional<Employe> getLeaveApprover(){
        Optional<ClientProjectSubteam>  clientProjectSubteam = findClientProjectSubTeamByEmployeeId(loggedIn.getUser().getEmployeId().getId());
        if(!clientProjectSubteam.isEmpty()) {
            return findClientProjectSubTeamManager(clientProjectSubteam.get().getId());
        }else{
           return Optional.ofNullable(null);
        }

    }

    public List<ClientProjectSubteamMember> findAllEmployeeByNameOrNumber(EmployeeSearch employeeSearch){
             Employe employee = new Employe();
                employee.setFirstName(employeeSearch.getSearchKey());
            return clientProjectSubteamMemberRepository.findAllByEmployeeFirstNameLikeAndOrganisation(employeeSearch.getSearchKey(), loggedIn.getOrg());
    }
}
