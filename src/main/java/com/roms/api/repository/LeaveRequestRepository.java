package com.roms.api.repository;

import com.roms.api.model.LeaveRequest;
import com.roms.api.model.Organisation;
import com.roms.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,String> {

    List<LeaveRequest> findAllByApproverAndOrganisation(Users approver, Organisation organisation);

    List<LeaveRequest> findAllByApproverAndLeaveStatusAndOrganisation(Users approver, int leaveStatus, Organisation organisation);

    List<LeaveRequest> findAllByUserIdAndOrganisation(Users approver,Organisation organisation);

    List<LeaveRequest> findAllByUserIdAndLeaveStatusAndOrganisation(Users approver,int leaveStatus, Organisation organisation);


}
