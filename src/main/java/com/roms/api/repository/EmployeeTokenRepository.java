package com.roms.api.repository;

import com.roms.api.model.EmployeeToken;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeTokenRepository extends JpaRepository<EmployeeToken,String> {


    Optional<EmployeeToken> findByCodeAndOrganisation(String code, Organisation org);

    List<EmployeeToken> findAllByCodeContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}
