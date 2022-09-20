package com.roms.api.repository;

import com.roms.api.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeResignationRepository extends JpaRepository<EmployeeResignation,String> {

    public List<EmployeeResignation> findAllByEmployeeAndOrganisationOrderByApplyDateDesc(Employe employee, Organisation org);

    public Optional<EmployeeResignation> findByEmployeeAndApproverAndOrganisationAndStatusIsNot(Employe employee,Employe approver, Organisation org,int status);
    public Page<EmployeeResignation> findAllByApproverAndOrganisation(Employe employee,  Organisation org, PageRequest pageable) ;

    public Page<EmployeeResignation> findAllByApproverAndOrganisationAndStatusGreaterThan(Employe employee,  Organisation org,int statues, PageRequest pageable) ;
    public Page<EmployeeResignation> findAllByApproverAndStatusAndOrganisation(Employe employee, int resigneStatus, Organisation org, PageRequest pageable) ;

    Page<EmployeeResignation> findAllByApplyDateBetweenAndOrganisation(Instant fromDate, Instant todate, Organisation organisation, PageRequest pageRequest);

    Page<EmployeeResignation> findAllByEmployeeEmployeTypeAndEmployeeDepartmentsAndApplyDateBetweenAndOrganisationAndStatus(EmployeType employeType, Departments departments,Instant fromdate, Instant toDate, Organisation organisation,int status, PageRequest pageRequest);

    Page<EmployeeResignation> findAllByEmployeeDepartmentsAndApplyDateBetweenAndOrganisationAndStatus( Departments departments,Instant fromdate, Instant toDate, Organisation organisation, int status,PageRequest pageRequest);

    Page<EmployeeResignation> findAllByEmployeeEmployeTypeAndApplyDateBetweenAndOrganisationAndStatus( EmployeType employeType,Instant fromdate, Instant toDate, Organisation organisation, int status,PageRequest pageRequest);

    Page<EmployeeResignation> findAllByEmployeeEmployeTypeAndEmployeeDepartmentsAndApplyDateBetweenAndOrganisation( EmployeType employeType,Departments departments,Instant fromdate, Instant toDate, Organisation organisation,PageRequest pageRequest);

    Page<EmployeeResignation> findAllByEmployeeDepartmentsAndApplyDateBetweenAndOrganisation( Departments departments,Instant fromdate, Instant toDate, Organisation organisation,PageRequest pageRequest);

    Page<EmployeeResignation> findAllByEmployeeEmployeTypeAndApplyDateBetweenAndOrganisation( EmployeType employeType,Instant fromdate, Instant toDate, Organisation organisation,PageRequest pageRequest);

    Page<EmployeeResignation> findAllByApplyDateBetweenAndOrganisationAndStatus( Instant fromdate, Instant toDate, Organisation organisation, int status ,PageRequest pageRequest);



}
