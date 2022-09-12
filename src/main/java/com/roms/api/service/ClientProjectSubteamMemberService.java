package com.roms.api.service;
import com.roms.api.model.*;
import com.roms.api.repository.ClientProjectSubteamMemberRepository;
import com.roms.api.requestInput.SearchInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class ClientProjectSubteamMemberService {
    @Autowired
    private ClientProjectSubteamMemberRepository clientProjectSubteamMemberRepository;
   @Autowired
   private LoggedInUserDetails loggedIn;

   @Autowired
   private EmployeeManagerService employeeManagerService;


    public void save(ClientProjectSubteamMember model){
        model.setOrganisation(loggedIn.getOrg());
        model.setCreateDate(Instant.now());
        model.setCreateBy(loggedIn.getUser());
        clientProjectSubteamMemberRepository.save(model);
    }

    public List<ClientProjectSubteamMember> findAll(){
        return clientProjectSubteamMemberRepository.findByOrganisation(loggedIn.getOrg());
    }

    public Optional<ClientProjectSubteam> findClientProjectSubTeamByEmployeeId(String employeeId){
        List<ClientProjectSubteamMember> clientProjectSubteamMember = clientProjectSubteamMemberRepository.findByEmployeeAndOrganisationAndManagerFlag(new Employe(employeeId), loggedIn.getOrg(),false);
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
        Optional<EmployeeManagers>  manger = employeeManagerService.getManager(loggedIn.getUser().getEmployeId().getId());
        if(!manger.isEmpty()){
            return Optional.ofNullable(manger.get().getManagers());

        }else{
            return  Optional.ofNullable(null);
        }
    }

    public List<ClientProjectSubteamMember> findAllEmployeeByNameOrNumber(SearchInput employeeSearch){
             Employe employee = new Employe();
                employee.setFirstName(employeeSearch.getSearchKey());
            return clientProjectSubteamMemberRepository.findAllByEmployeeFirstNameLikeAndOrganisation(employeeSearch.getSearchKey(), loggedIn.getOrg());
    }
}
