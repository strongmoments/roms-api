package com.roms.api.repository;


import com.roms.api.model.EmployeeSkilsLicence;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLicenceRepository extends JpaRepository<EmployeeSkilsLicence, String> {


    Optional<EmployeeSkilsLicence> findByCodeAndOrganisation(String code, Organisation org);

    List<EmployeeSkilsLicence> findAllByCodeContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}
