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

    Page<LeaveRequest> findAllByApplyDateBetweenAndOrganisationOrderByApplyDateDesc( Instant fromDate,Instant toDate,Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByLeaveStatusAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc( int leaveStatus, Instant fromDate,Instant toDate, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeDepartmentsAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(Departments departments,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);
    Page<LeaveRequest> findAllByEmployeDepartmentsAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(Departments departments,Instant fromDate,Instant toDate,Organisation organisation,int leaveStatus, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);
    Page<LeaveRequest> findAllByEmployeEmployeTypeAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(EmployeType employeType,Instant fromDate,Instant toDate,Organisation organisation, int leaveStatus,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Departments departments,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(EmployeType employeType,Departments departments,Instant fromDate,Instant toDate,Organisation organisation, int leavestatus, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc( Instant fromDate,Instant toDate,Organisation organisation,PageRequest pageRequest);

  //  Page<LeaveRequest> findAllByLeaveStatusAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc( int leaveStatus, Instant fromDate,Instant toDate, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByLeaveStatusAndEndDateTimeBetweenOrStartDateTimeAfterAndEndDateTimeBeforeAndOrganisationOrderByApplyDateDesc( int leaveStatus, Instant fromDate,Instant toDate,  Instant fromDate1,Instant toDate1, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(Departments departments,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);
    Page<LeaveRequest> findAllByEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(Departments departments,Instant fromDate,Instant toDate,Organisation organisation,int leaveStatus, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);
    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEndDateTimeBetweenAndOrganisationAndLeaveStatus(EmployeType employeType,Instant fromDate,Instant toDate,Organisation organisation, int leaveStatus,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Departments departments,Instant fromDate,Instant toDate,Organisation organisation, PageRequest pageRequest);


    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(EmployeType employeType,Departments departments,Instant fromDate,Instant toDate,Organisation organisation, int leavestatus, PageRequest pageRequest);




}
