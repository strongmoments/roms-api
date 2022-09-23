package com.roms.api.repository;


import com.roms.api.model.EmployeeCertificate;
import com.roms.api.model.EmployeeLicence;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeLicenceRepository extends JpaRepository<EmployeeLicence, String> {


    Optional<EmployeeLicence> findByCodeAndOrganisation(String code, Organisation org);

    List<EmployeeLicence> findAllByCodeContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}
