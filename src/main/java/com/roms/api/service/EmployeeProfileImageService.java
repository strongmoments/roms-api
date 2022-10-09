package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeProfileImage;
import com.roms.api.repository.EmployeeProfileImageRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmployeeProfileImageService {

    @Autowired
    private EmployeeProfileImageRepository employeeProfileImageRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public EmployeeProfileImage save(EmployeeProfileImage employeeProfileImage){
        employeeProfileImage.setCreateBy(loggedIn.getUser());
        employeeProfileImage.setCreateDate(Instant.now());
        return employeeProfileImageRepository.save(employeeProfileImage);

    }

    public EmployeeProfileImage update(EmployeeProfileImage employeeProfileImage){
        employeeProfileImage.setLastUpdateDate(Instant.now());
        employeeProfileImage.setUpdateBy(loggedIn.getUser());
        return employeeProfileImageRepository.save(employeeProfileImage);

    }

    public EmployeeProfileImage findEmployeImage(){
      List<EmployeeProfileImage> employeeProfileImageList = employeeProfileImageRepository.findAllByEmployeAndOrganisation(loggedIn.getUser().getEmployeId(), loggedIn.getOrg());
      if(!employeeProfileImageList.isEmpty()){
        return employeeProfileImageList.get(0);
      }
      return  null;

    }
}
