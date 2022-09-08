package com.roms.api.repository;

import com.roms.api.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,String> {

    Page<LeaveRequest> findAllByApproverAndOrganisationAndLeaveStatusGreaterThanOrderByApplyDateDesc(Employe approver, Organisation organisation,int leaveStatus,PageRequest pageRequest);

    Page<LeaveRequest> findAllByApproverAndOrganisationOrderByApplyDateDesc(Employe approver, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByApproverAndLeaveStatusAndOrganisationOrderByApplyDateDesc(Employe approver, int leaveStatus, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeAndOrganisationOrderByApplyDateDesc(Employe requester, Organisation organisation, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeAndLeaveStatusAndOrganisationOrderByApplyDateDesc(Employe requester,int leaveStatus, Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByOrganisationOrderByApplyDateDesc( Organisation organisation,PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeDepartmentsAndOrganisationOrderByApplyDateDesc(Departments departments,Organisation organisation, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Organisation organisation, PageRequest pageRequest);

    Page<LeaveRequest> findAllByEmployeEmployeTypeAndEmployeDepartmentsAndOrganisationOrderByApplyDateDesc(EmployeType employeType,Departments departments,Organisation organisation, PageRequest pageRequest);


}
