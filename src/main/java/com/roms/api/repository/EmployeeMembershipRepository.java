package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeMembership;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeMembershipRepository extends JpaRepository<EmployeeMembership, String> {

    Optional<EmployeeMembership> findByEmployeAndOrganisation(Employe employe, Organisation organisation);

    List<EmployeeMembership> findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);
}
