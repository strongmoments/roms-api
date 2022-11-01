package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeBanks;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeBankRepository  extends JpaRepository<EmployeeBanks,String> {

    List<EmployeeBanks> findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);


    Optional<EmployeeBanks> findByEmployeAndOrganisationAndBankType(Employe employe, Organisation organisation,Integer banktype);
}
