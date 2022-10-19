package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeEmergencyContact;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeEmergencyContactRepository extends JpaRepository<EmployeeEmergencyContact, String> {

    List<EmployeeEmergencyContact> findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);
}
