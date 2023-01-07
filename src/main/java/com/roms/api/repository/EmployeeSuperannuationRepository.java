package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeSuperannuation;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeSuperannuationRepository extends JpaRepository<EmployeeSuperannuation, String> {
    List<EmployeeSuperannuation> findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);

    Optional<EmployeeSuperannuation> findByEmployeAndOrganisationAndFundType(Employe employe, Organisation organisation,Integer fundType);
}
