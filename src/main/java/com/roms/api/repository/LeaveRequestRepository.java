package com.roms.api.repository;

import com.roms.api.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,String> {

    Page<LeaveRequest> findAllByApproverAndOrganisationAndLeaveStatusGreaterThanOrderByApplyDateDesc(Employe approver, Organisation organisation,int leaveStatus,PageRequest pageRequest);

    Page<LeaveRequest> findAllByApproverAndOrganisationOrderByApplyDateDesc(Employe approver, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByApproverAndLeaveStatusAndOrganisationOrderByApplyDateDesc(Employe approver, int leaveStatus, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeAndOrganisationOrderByApplyDateDesc(Employe requester, Organisation organisation, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeAndLeaveStatusAndOrganisationOrderByApplyDateDesc(Employe requester,int leaveStatus, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByOrganisationOrderByApplyDateDesc( Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeDepartmentsAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(Departments departments,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);
    Page<LeaveRequest> findAllByEmployeDepartmentsAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(Departments departments,Instant fromDate,Instant toDate,Organisation organisation,int leaveStatus, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);
    Page<LeaveRequest> findAllByEmployeEmployeTypeAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(EmployeType employeType,Instant fromDate,Instant toDate,Organisation organisation, int leaveStatus,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Departments departments,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(EmployeType employeType,Departments departments,Instant fromDate,Instant toDate,Organisation organisation, int leavestatus, PageRequest pageRequest);




}
