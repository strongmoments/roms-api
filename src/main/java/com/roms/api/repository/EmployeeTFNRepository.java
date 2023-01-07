package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeTFN;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeTFNRepository extends JpaRepository<EmployeeTFN,String> {

    List<EmployeeTFN> findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);

}
