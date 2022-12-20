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

   @Autowired
   private EmploymentRecommendService employmentRecommendService;


    public void save(ClientProjectSubteamMember model){
        model.setOrganisation(loggedIn.getOrg());
        model.setCreateDate(Instant.now());
        model.setCreateBy(loggedIn.getUser());
        clientProjectSubteamMemberRepository.save(model);
    }

    public void save2(ClientProjectSubteamMember model){
        Organisation org = new Organisation("ab905406-79a3-4e54-8244-d79fc0e60937");
        model.setOrganisation(org);
        model.setCreateDate(Instant.now());
         clientProjectSubteamMemberRepository.save(model);
    }
    public void update(ClientProjectSubteamMember model){
        model.setLastUpdateDate(Instant.now());
        model.setUpdateBy(loggedIn.getUser());
        clientProjectSubteamMemberRepository.save(model);
    }

    public void update2(ClientProjectSubteamMember model){
        model.setLastUpdateDate(Instant.now());
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

    public List<ClientProjectSubteamMember> findAllEmployeeByFirstNameOrNumber(String searchText){
            return clientProjectSubteamMemberRepository.findAllByEmployeeEmployeeNoContainsIgnoreCaseAndManagerFlagOrEmployeeFirstNameContainsIgnoreCaseAndManagerFlagAndOrganisation(searchText,false,searchText,false, loggedIn.getOrg());
    }

    public void transferToGang(String employeeRecommendationId){
        Optional<EmploymentRecommendation>  employmentRecommendation =   employmentRecommendService.findById(employeeRecommendationId);
        if(employmentRecommendation.isPresent()){
            EmploymentRecommendation employmentRecommendation1 =   employmentRecommendation.get();
            Employe employe = employmentRecommendation1.getEmployeeIdx();
            ClientProjectSubteam clientProjectSubteam = employmentRecommendation1.getToSubteamIdx();
            List<ClientProjectSubteamMember> clientProjectSubteamMember = clientProjectSubteamMemberRepository.findByEmployee(employe);
            if(!clientProjectSubteamMember.isEmpty()){
                clientProjectSubteamMember.forEach(obj->{
                    obj.setClientProjectSubteam(clientProjectSubteam);
                    update2(obj);
                });
            }else{
                ClientProjectSubteamMember model = new ClientProjectSubteamMember();
                model.setEmployee(employe);
                model.setClientProjectSubteam(clientProjectSubteam);
                model.setStartDate(employmentRecommendation1.getDemandIdx().getPerposedDate());
                model.setManagerFlag(false);
                save2(model);
            }
            employmentRecommendation1.setJobFlag(1);
            employmentRecommendService.update2(employmentRecommendation1);

        }

    }


}
