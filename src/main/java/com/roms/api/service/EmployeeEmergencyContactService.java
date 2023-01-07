package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeEmergencyContact;
import com.roms.api.repository.EmployeeEmergencyContactRepository;
import com.roms.api.requestInput.OnboardingEmergencyContactInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmployeeEmergencyContactService {

    @Autowired
    private EmployeeEmergencyContactRepository employeeEmergencyContactRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeEmergencyContact save(EmployeeEmergencyContact employeeEmergencyContact){
            employeeEmergencyContact.setOrganisation(loggedIn.getOrg());
            employeeEmergencyContact.setCreateBy(loggedIn.getUser());
            employeeEmergencyContact.setCreateDate(Instant.now());
            employeeEmergencyContact.setEmploye(loggedIn.getUser().getEmployeId());

            return employeeEmergencyContactRepository.save(employeeEmergencyContact);

    }

    public EmployeeEmergencyContact saveFromOnboarding(OnboardingEmergencyContactInput request){
        EmployeeEmergencyContact model = findEmergencyContact();
        EmployeeEmergencyContact employeeEmergencyContact = EmployeeEmergencyContact.builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress().getAddress())
                .mobile(request.getMobile())
                .middleName(request.getMiddleName())
                .salut(request.getSalut())
                .state(request.getAddress().getState())
                .suburb(request.getAddress().getSuburb())
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .relationship(request.getRelationship())
                .postcode(request.getAddress().getPostcode())
                .salut(request.getSalut())
                 .build();
        if(model != null){
            employeeEmergencyContact.setId(model.getId());
        }
        return save(employeeEmergencyContact);

    }

    public List<EmployeeEmergencyContact>  findEmergencyContactByEmployeeId(String employId){
       return  employeeEmergencyContactRepository.findAllByEmployeAndOrganisation(new Employe(employId),loggedIn.getOrg());
    }

    public EmployeeEmergencyContact  findEmergencyContact(){
        List<EmployeeEmergencyContact> emergencyContacts =   employeeEmergencyContactRepository.findAllByEmployeAndOrganisation(loggedIn.getUser().getEmployeId(), loggedIn.getOrg());
        if(!emergencyContacts.isEmpty()){
            return emergencyContacts.get(0);
        }
        return null;
    }


}
