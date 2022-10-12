package com.roms.api.service;


import com.roms.api.model.*;
import com.roms.api.repository.EmployeRepository;
import com.roms.api.requestInput.OnboardingPersonalDetailInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository employeesRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private EmployeeAddressService employeeAddressService;

    @Autowired EmployeeProfileImageService employeeProfileImageService;




    public void uploadPic(DigitalAssets digitalAssets, String empployeeId){
        Employe employe = new Employe();
        employe.setId(empployeeId);

    EmployeeProfileImage  employeeImage =  employeeProfileImageService.findEmployeImage(employe);
        if(employeeImage != null){
            employeeImage.setDigitalAssets(digitalAssets);
            employeeProfileImageService.update(employeeImage);
        }else{
            EmployeeProfileImage employeeImage1 = new EmployeeProfileImage();
            employeeImage1.setEmploye(employe);
            employeeImage1.setDigitalAssets(digitalAssets);
            employeeProfileImageService.save(employeeImage1);

        }

    }



    public Optional<Employe> findById(String employeeId){
        return employeesRepository.findById(employeeId);

    }
    public Employe save(Employe employeModel){
        return employeModel = employeesRepository.save(employeModel);

    }

    public List<Employe> findAllManagers(){
        return employeesRepository.findAllByManagerFlagAndOrganisation(true,loggedIn.getOrg());

    }

    public Employe update(OnboardingPersonalDetailInput request){
       Employe employeModel = loggedIn.getUser().getEmployeId();
        employeModel.setFirstName(request.getFirstName());
        employeModel.setEmployeeNo(request.getEmployeeNo());
        employeModel.setLastName(request.getLastName());
        employeModel.setEmail(request.getEmail());
        employeModel.setPhone(request.getPhone());
        employeModel.setMiddleName(request.getMiddleName());
        employeModel.setNickName(request.getNickName());
        employeModel.setPhoneticName(request.getPhoneticName());
        employeModel.setJobTitle(request.getJobTitle());
        employeModel.setPronoun(request.getPronoun());
        employeModel.setSalut(request.getSalut());
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Instant dob = sdf.parse(request.getBirthdate()).toInstant();
            employeModel.setBirthdate(dob);
        }catch (Exception e){

        }

        employeModel.setGender(request.getGender());
        employeModel.setIndigenousFlag(request.getIndigenousFlag());
        employeModel.setLastUpdateDate(Instant.now());
        employeModel.setUpdateBy(loggedIn.getUser());
        employeModel = employeesRepository.save(employeModel);

        EmployeeAddress employeePermanentAddress = EmployeeAddress.builder()
                .type(1)
                .suburb(request.getPermanentAddress().getSuburb())
                .state(request.getPermanentAddress().getState())
                .postcode(request.getPermanentAddress().getPostcode())
                .employe(employeModel)
                .build();

        employeeAddressService.save(employeePermanentAddress);
        EmployeeAddress employeeTempddress = EmployeeAddress.builder()
                .type(2)
                .suburb(request.getTempAddress().getSuburb())
                .state(request.getTempAddress().getState())
                .postcode(request.getTempAddress().getPostcode())
                .employe(employeModel)
                .build();
        employeeAddressService.save(employeeTempddress);

        DigitalAssets digitalAssets = DigitalAssets.builder().build();
        digitalAssets.setId(request.getProfileImageId());
        EmployeeProfileImage employeeProfileImage = EmployeeProfileImage.builder().
                employe(employeModel)
                        .digitalAssets(digitalAssets).
                build();

        employeeProfileImageService.save(employeeProfileImage);
        return employeModel;

    }

    public Employe update(Employe employeModel){
        employeModel.setUpdateBy(loggedIn.getUser());
        employeModel.setLastUpdateDate(Instant.now());
        return employeModel = employeesRepository.save(employeModel);

    }

    public Optional<Employe> findByEmployeeId(String employeeId){
        return  employeesRepository.findById(employeeId);
    }

    public Page<Employe> findAll(int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("lastName","firstName").ascending());
        Map<String,Object> loggedInUserDetails =(Map<String,Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return employeesRepository.findAllByOrganisation(new Organisation(loggedInUserDetails.get("orgId").toString()),pageble);
    }

    public Page<Employe> findAllFilterByName(int page, int size, String searchText){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("lastName","firstName").ascending());
        Map<String,Object> loggedInUserDetails =(Map<String,Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return employeesRepository.findAllByOrganisationAndFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(new Organisation(loggedInUserDetails.get("orgId").toString()),searchText,searchText, pageble);
    }



    public Long getTotalEmployeeCount(){
       return employeesRepository.countAllByOrganisation(loggedIn.getOrg());
    }

    public Long getNewEmployeeCountBetween(Instant fromDate, Instant toDate){
        return employeesRepository.countAllByOrganisationAndCreateDateBetween(loggedIn.getOrg(),fromDate,toDate);
    }

    public List<Instant> findDobOfEmployees(){
        return employeesRepository.findBirthDateByOrganisation(loggedIn.getOrg().getId());
    }


}
