package com.roms.api.service;

import com.roms.api.model.EmployeeMembership;
import com.roms.api.repository.EmployeeMembershipRepository;
import com.roms.api.requestInput.OnboardingMembershipInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeMembershipService {

    @Autowired
    private EmployeeMembershipRepository employeeMembershipRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;


    public EmployeeMembership save(EmployeeMembership employeeMembership){
        employeeMembership.setOrganisation(loggedIn.getOrg());
        employeeMembership.setCreateBy(loggedIn.getUser());
        employeeMembership.setCreateDate(Instant.now());

        return employeeMembershipRepository.save(employeeMembership);

    }
    public  EmployeeMembership findByEmployee(){
        Optional<EmployeeMembership> empMembershipResult =  employeeMembershipRepository.findByEmployeAndOrganisation(loggedIn.getUser().getEmployeId(), loggedIn.getOrg());
        if(empMembershipResult.isPresent()){
            return  empMembershipResult.get();
        }
        return  null;

    }


    public EmployeeMembership saveOnboarding(OnboardingMembershipInput request){
        EmployeeMembership employeeMembership = findByEmployee();
        EmployeeMembership employeeMembershipModel = EmployeeMembership.builder()
                .longServiceLeaveScheme(request.isLongServiceLeaveScheme())
                .redundancyScheme(request.isRedundancyScheme())
                .redundancySchemeName(request.getRedundancySchemeName())
                .redundancySchemeMemberShipNo(request.getRedundancySchemeMemberShipNo())
                .longServiceSchemeName(request.getLongServiceSchemeName())
                .employe(loggedIn.getUser().getEmployeId())
                .longServiceSchemeMemberShipNo(request.getLongServiceSchemeMemberShipNo())
                .build();
        if(employeeMembership != null){
            employeeMembershipModel.setId(employeeMembership.getId());
        }
        return save(employeeMembershipModel);


    }

}
