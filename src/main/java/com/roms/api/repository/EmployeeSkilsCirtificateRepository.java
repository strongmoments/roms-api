package com.roms.api.repository;

import com.roms.api.model.EmployeeSkilsCirtificate;
import com.roms.api.model.EmployeeSkilsPlant;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeSkilsCirtificateRepository extends JpaRepository<EmployeeSkilsCirtificate, String> {


    Optional<EmployeeSkilsCirtificate> findByCodeAndOrganisation(String code, Organisation org);

    List<EmployeeSkilsCirtificate> findAllByCodeContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}

