package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeDevices;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDeviceRepository extends JpaRepository<EmployeeDevices,String> {

    Optional<EmployeeDevices> findByNotificationDeviceTokenAndOrganisation(String id, Organisation org);

    List<EmployeeDevices> findAllByEmployeAndOrganisation(Employe emplyee, Organisation org);

    List<EmployeeDevices> findAllByEmploye(Employe emplyee);


}
