package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeResignation;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeResignationRepository extends JpaRepository<EmployeeResignation,String> {

    public Optional<EmployeeResignation> findByEmployeeAndOrganisation(Employe employee, Organisation org);

    public Optional<EmployeeResignation> findByEmployeeAndApproverAndOrganisation(Employe employee,Employe approver, Organisation org);
    public Page<EmployeeResignation> findAllByApproverAndOrganisation(Employe employee,  Organisation org, PageRequest pageable) ;

    public Page<EmployeeResignation> findAllByApproverAndOrganisationAndStatusGreaterThan(Employe employee,  Organisation org,int statues, PageRequest pageable) ;
    public Page<EmployeeResignation> findAllByApproverAndStatusAndOrganisation(Employe employee, int resigneStatus, Organisation org, PageRequest pageable) ;


}
