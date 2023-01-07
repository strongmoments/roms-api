package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeAddress;
import com.roms.api.model.Organisation;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, String> {

   public List<EmployeeAddress> findByEmployeAndOrganisation(Employe employe, Organisation organisation) ;

    Optional<EmployeeAddress> findByEmployeAndOrganisationAndType(Employe employe, Organisation organisation, int
                                                                   type);
}
