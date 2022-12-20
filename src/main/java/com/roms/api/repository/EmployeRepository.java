package com.roms.api.repository;


import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe,String> {

    Page<Employe> findAllByOrganisation(Organisation organisation, PageRequest pageRequest);

    Page<Employe> findAllByOrganisationAndFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(Organisation organisation,String searchText, String searchText2, PageRequest pageRequest);
    Long countAllByOrganisation(Organisation organisation);
    Long countAllByOrganisationAndCreateDateBetween(Organisation organisation , Instant fromDate, Instant todate);

    @Query("SELECT p.birthdate from Employe p where p.organisation.id =?1")
    List<Instant> findBirthDateByOrganisation(String organisation);

    List<Employe> findAllByManagerFlagAndOrganisation(boolean managerFlag, Organisation organisation);

    List<Employe> findAllByManagerFlag(boolean managerFlag);

    List<Employe> findAllByManagerFlagAndOrganisationAndLastNameContainsIgnoreCase(boolean managerflag,Organisation organisation,String searchText);
}
