package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeLicence;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLicenceRepository extends JpaRepository<EmployeeLicence, String> {

    Optional<EmployeeLicence> findByEmployeAndOrganisation(Employe employe, Organisation organisation);
    List<EmployeeLicence> findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);
}
