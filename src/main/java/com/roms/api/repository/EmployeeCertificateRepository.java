package com.roms.api.repository;

import com.roms.api.model.EmployeeCertificate;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeCertificateRepository extends JpaRepository<EmployeeCertificate, String> {

    Optional<EmployeeCertificate>  findByCodeAndOrganisation(String code, Organisation org);

    List<EmployeeCertificate> findAllByCodeContainingIgnoreCaseAndOrganisation(String searchText, Organisation org);
}

