package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeManagers;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeManagerRepository extends JpaRepository<EmployeeManagers, String> {

    Optional<EmployeeManagers> findByEmployeAndOrganisation(Employe employe, Organisation org);
}
