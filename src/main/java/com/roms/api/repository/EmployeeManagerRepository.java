package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeManagerRepository extends JpaRepository<EmployeeManagers, String> {

    Optional<EmployeeManagers> findByEmployeAndOrganisation(Employe employe, Organisation org);
    List<EmployeeManagers> findByEmployeAndManagersAndOrganisation(Employe employe, Employe manager, Organisation org);

    Optional<EmployeeManagers> findByEmployeAndAndOrganisation(Employe employe, Organisation org);

    List<EmployeeManagers>  findAllByManagersAndEmployeManagerFlag(Employe employe, boolean managerflag);

    List<EmployeeManagers> findAllByManagersFirstNameContainingIgnoreCaseOrManagersLastNameContainingIgnoreCaseAndOrganisation(String searchText, String searchText2,  Organisation org);

    List<EmployeeManagers> findAllByManagersInAndEmployeFirstNameContainingIgnoreCaseAndOrganisation(List<Employe> manager, String searchText,   Organisation org);
}
