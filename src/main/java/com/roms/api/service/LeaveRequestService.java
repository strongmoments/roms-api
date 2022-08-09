package com.roms.api.service;

import com.roms.api.constant.Constant;
import com.roms.api.model.LeaveRequest;
import com.roms.api.model.LeaveType;
import com.roms.api.model.Organisation;
import com.roms.api.model.Users;
import com.roms.api.repository.LeaveRequestRepository;
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
    Map<String,Object> loggedInUser = (Map<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();

    public void LeaveRequestRepository(LeaveRequest model) {
        leaveRequestRepository.save(model);
    }

    public List<LeaveRequest> findAllByApprover(String approverId){
       return leaveRequestRepository.findAllByApproverAndOrganisation(new Users(approverId), new Organisation((String) loggedInUser.get(Constant.ORG_ID)));
    }

    public List<LeaveRequest> findAllByApproverAndLeaveStatus(String approverId,int leaveStatus){
        return leaveRequestRepository.findAllByApproverAndLeaveStatusAndOrganisation(new Users(approverId), leaveStatus, new Organisation((String) loggedInUser.get(Constant.ORG_ID)));
    }

    public List<LeaveRequest> findAllByUserId(String userId){
        return leaveRequestRepository.findAllByUserIdAndOrganisation(new Users(userId), new Organisation((String) loggedInUser.get(Constant.ORG_ID)));
    }

    public List<LeaveRequest> findAllByUserIdAndLeaveStatus(String userId,int leaveTatus){
        return leaveRequestRepository.findAllByUserIdAndLeaveStatusAndOrganisation(new Users(userId), leaveTatus, new Organisation((String) loggedInUser.get(Constant.ORG_ID)));
    }

    public LeaveRequest applyLeave(LeaveRequest leaveRequest, String clientProjectSubTeamId){
        leaveRequest.setOrganisation(leaveRequest.getOrganisation());
        leaveRequest.setCreateBy((Users) loggedInUser.get(Constant.USER_ID));
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
            leaveRequestMole.setDeniedReason(request.getDeniedReason());
            leaveRequestRepository.save(leaveRequestMole);
        }
    }


}
