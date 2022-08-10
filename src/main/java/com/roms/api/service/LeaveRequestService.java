package com.roms.api.service;

import com.roms.api.constant.Constant;
import com.roms.api.model.*;
import com.roms.api.repository.LeaveRequestRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<LeaveRequest> findAllRecievedRequest(String approverId,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
       return leaveRequestRepository.findAllByApproverAndOrganisation(new Employe(approverId), loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllRecievedRequestByLeaveStatus(String approverId,int leaveStatus ,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByApproverAndLeaveStatusAndOrganisation(new Employe(approverId), leaveStatus, loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllSentRequest(String employeeId, int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByEmployeAndOrganisation(new Employe(employeeId), loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllSentRequestByLeaveStatus(String employeeId,int leaveTatus,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByEmployeAndLeaveStatusAndOrganisation(new Employe(employeeId), leaveTatus,loggedIn.getOrg(),pageble);
    }

    public LeaveRequest applyLeave(LeaveRequest leaveRequest){
        leaveRequest.setOrganisation(leaveRequest.getOrganisation());
        leaveRequest.setCreateBy(loggedIn.getUser());
        leaveRequest.setCreateDate(Instant.now());
        leaveRequest.setApplyDate(Instant.now());
        leaveRequest.setLeaveStatus(1);
        // featching employee team or gang
        Optional<ClientProjectSubteam>  clientProjectSubteam = clientProjectSubteamMemberService.findClientProjectSubTeamByEmployeeId(leaveRequest.getEmploye().getId());
        if(!clientProjectSubteam.isEmpty()){
            Optional<Employe> manager  = clientProjectSubteamMemberService.findClientProjectSubTeamManager(clientProjectSubteam.get().getId());
            if(manager.isEmpty()){
                // @todo throw exception manager not found
            }else{
                leaveRequest.setApprover(manager.get());
            }
            leaveRequest = leaveRequestRepository.save(leaveRequest);
        }else{
            // @todo throw exception employee is not associate with gang
        }

        return  leaveRequest;
    }

    public void approveLeave(LeaveRequest leaveRequest){
        Optional<LeaveRequest> leaveRequestModel =  leaveRequestRepository.findById(leaveRequest.getId());
        if(!leaveRequestModel.isEmpty()){
            LeaveRequest leaveRequestMole = leaveRequestModel.get();
            leaveRequestMole.setLeaveStatus(2);
            leaveRequestMole.setReviewerRemark(leaveRequest.getReviewerRemark());
            leaveRequestMole.setDateOfApproval(Instant.now());
            leaveRequestMole.setUpdateBy(loggedIn.getUser());
            leaveRequestMole.setLastUpdateDate(Instant.now());
            leaveRequestRepository.save(leaveRequestMole);
        }else{
            //@todo throw exception leave request not found
        }

    }

    public void rejectLeave(LeaveRequest request){
        Optional<LeaveRequest> leaveRequest =  leaveRequestRepository.findById(request.getId());
        if(!leaveRequest.isEmpty()){
            LeaveRequest leaveRequestMole = leaveRequest.get();
            leaveRequestMole.setLeaveStatus(3);
            leaveRequestMole.setReviewerRemark(request.getReviewerRemark());
            leaveRequestMole.setDateOfApproval(Instant.now());
            leaveRequestMole.setUpdateBy(loggedIn.getUser());
            leaveRequestMole.setLastUpdateDate(Instant.now());
            leaveRequestRepository.save(leaveRequestMole);

        }else{
            //@todo throw exception leave request not found
        }
    }


}
