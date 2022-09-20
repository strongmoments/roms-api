package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeDevices;
import com.roms.api.model.Organisation;
import com.roms.api.repository.EmployeeDeviceRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDeviceService {

    @Autowired
    private EmployeeDeviceRepository employeeDeviceRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    public void save(EmployeeDevices employeeDevices){
        if(!findByNotificatoinDeviceId(employeeDevices.getNotificationDeviceToken())){
            employeeDevices.setEmploye(loggedIn.getUser().getEmployeId());
            employeeDevices.setCreateDate(Instant.now());
            employeeDevices.setCreateBy(loggedIn.getUser());
            employeeDevices.setOrganisation(loggedIn.getOrg());
            employeeDeviceRepository.save(employeeDevices);
        }
    }

    public List<EmployeeDevices> findAllByEmployee(String employeeId,String orgId){
       Employe employee =  new Employe();
       employee.setId(employeeId);
        Organisation org = new Organisation();
        org.setId(orgId);
        return employeeDeviceRepository.findAllByEmployeAndOrganisation(employee, org);
    }

    public List<String> findAllResisterdDeviceOfEmployee(String employeeId,String orgId){
        Organisation org =new Organisation();
        org.setId(orgId);
        List<EmployeeDevices> notificatinDevices = findAllByEmployee(employeeId,orgId);
        List<String> allDevices = new ArrayList<>();
        if(!notificatinDevices.isEmpty()){
            notificatinDevices.forEach(obj->{
                allDevices.add(obj.getNotificationDeviceToken());
            });
        }

        return allDevices;
    }

    public boolean findByNotificatoinDeviceId(String noficationDeviceId){
     Optional<EmployeeDevices> employeeDevices = employeeDeviceRepository.findByNotificationDeviceTokenAndOrganisation(noficationDeviceId, loggedIn.getOrg());
     if(employeeDevices.isEmpty()){
         return  false;
     }else {
         return true;
     }
    }
}
