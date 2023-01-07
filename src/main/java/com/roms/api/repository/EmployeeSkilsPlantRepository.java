package com.roms.api.repository;

import com.roms.api.model.EmployeeSkilsLicence;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeSkilsPlantRepository  extends JpaRepository<EmployeeSkilsPlant, String> {


    Optional<EmployeeSkilsPlant> findByCodeAndOrganisation(String code, Organisation org);

    List<EmployeeSkilsPlant> findAllByCodeContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}
