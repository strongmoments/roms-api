package com.roms.api.service;

import com.roms.api.constant.Constant;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.LeaveType;
import com.roms.api.model.Organisation;
import com.roms.api.model.Users;
import com.roms.api.repository.LeaveRequestRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Constants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeaveRequestService {
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private  ClientProjectSubteamMemberService clientProjectSubteamMemberService;

    @Autowired
    private LoggedInUserDetails loggedIn;



    public void LeaveRequestRepository(LeaveRequest model) {
        leaveRequestRepository.save(model);
    }

    public List<LeaveRequest> findAllByApprover(String approverId){
       return leaveRequestRepository.findAllByApproverAndOrganisation(new Users(approverId), loggedIn.getOrg());
    }

    public List<LeaveRequest> findAllByApproverAndLeaveStatus(String approverId,int leaveStatus){
        return leaveRequestRepository.findAllByApproverAndLeaveStatusAndOrganisation(new Users(approverId), leaveStatus, loggedIn.getOrg());
    }

    public List<LeaveRequest> findAllByUserId(String userId){
        return leaveRequestRepository.findAllByUserIdAndOrganisation(new Users(userId), loggedIn.getOrg());
    }

    public List<LeaveRequest> findAllByUserIdAndLeaveStatus(String userId,int leaveTatus){
        return leaveRequestRepository.findAllByUserIdAndLeaveStatusAndOrganisation(new Users(userId), leaveTatus,loggedIn.getOrg());
    }

    public LeaveRequest applyLeave(LeaveRequest leaveRequest, String clientProjectSubTeamId){
        leaveRequest.setOrganisation(leaveRequest.getOrganisation());
        leaveRequest.setCreateBy(loggedIn.getUser());
        leaveRequest.setCreateDate(Instant.now());
        leaveRequest.setApplyDate(Instant.now());
        leaveRequest.setLeaveStatus(0);
        Optional<Users> manage = clientProjectSubteamMemberService.findClientProjectSubTeamManager(clientProjectSubTeamId);
                if(clientProjectSubTeamId.isEmpty()){
                    // @todo throw exception manager not found
                }else{
                    leaveRequest.setApprover(manage.get());
                }
        leaveRequest = leaveRequestRepository.save(leaveRequest);

        return  leaveRequest;
    }
    public void approveLeave(String leaveRequestId){
        Optional<LeaveRequest> leaveRequest =  leaveRequestRepository.findById(leaveRequestId);
        if(!leaveRequest.isEmpty()){
            LeaveRequest leaveRequestMole = leaveRequest.get();
            leaveRequestMole.setLeaveStatus(1);
            leaveRequestRepository.save(leaveRequestMole);
        }

    }

    public void rejectLeave(LeaveRequest request){
        Optional<LeaveRequest> leaveRequest =  leaveRequestRepository.findById(request.getId());
        if(!leaveRequest.isEmpty()){
            LeaveRequest leaveRequestMole = leaveRequest.get();
            leaveRequestMole.setLeaveStatus(2);
            leaveRequestRepository.save(leaveRequestMole);
        }
    }


}
