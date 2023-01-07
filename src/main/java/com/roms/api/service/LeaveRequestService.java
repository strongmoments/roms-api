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



    public Optional<LeaveRequest> findById(String id){
        return leaveRequestRepository.findById(id);
    }

    public LeaveRequest update(LeaveRequest request){
        request.setLastUpdateDate(Instant.now());
        request.setUpdateBy(loggedIn.getUser());
        return leaveRequestRepository.save(request);
    }

    public void LeaveRequestRepository(LeaveRequest model) {
        leaveRequestRepository.save(model);
    }

    public Page<LeaveRequest> findAllRecievedRequest(String approverId,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
       return leaveRequestRepository.findAllByApproverAndOrganisationOrderByApplyDateDesc(new Employe(approverId), loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllRecievedRequestHistory(String approverId,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("lastUpdateDate").descending());
        return leaveRequestRepository.findAllByApproverAndOrganisationAndLeaveStatusGreaterThanOrderByApplyDateDesc(new Employe(approverId), loggedIn.getOrg(),1,pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransaction(int page, int size,Instant fromdDate, Instant toDate){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByApplyDateBetweenAndOrganisationOrderByApplyDateDesc(fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransactionByLeaveStatus(int page, int size,Instant fromdDate, Instant toDate,Integer leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByLeaveStatusAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(leaveStatus,fromdDate,toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransactionByDepartment(int page, int size,Instant fromdDate, Instant toDate, String departmentId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeDepartmentsAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(departments,fromdDate,toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransactionByDepartmentAndLeaveStatus(int page, int size,Instant fromdDate, Instant toDate, String departmentId, Integer leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeDepartmentsAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(departments,fromdDate,toDate,loggedIn.getOrg(),leaveStatus,pageble);
    }
    public Page<LeaveRequest> findAllLeaveTransactionByEmployeeType(int page, int size,Instant fromdDate, Instant toDate,String employeeTypeId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(employeType,fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransactionByEmployeeTypeAndLeaveStatus(int page, int size,Instant fromdDate, Instant toDate,String employeeTypeId,Integer leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(employeType,fromdDate, toDate,loggedIn.getOrg(),leaveStatus,pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransactionByEmployeeTypeAndDepartment(int page, int size, Instant fromdDate, Instant toDate,String employeeTypeId, String departmentId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndEmployeDepartmentsAndApplyDateBetweenAndOrganisationOrderByApplyDateDesc(employeType,departments,fromdDate,toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllLeaveTransactionByEmployeeTypeAndDepartmentAndLeaveStatus(int page, int size, Instant fromdDate, Instant toDate,String employeeTypeId, String departmentId, int leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndEmployeDepartmentsAndApplyDateBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(employeType,departments,fromdDate,toDate,loggedIn.getOrg() ,leaveStatus,pageble);
    }




    public Page<LeaveRequest> findAllCurrentLeaveTransaction(int page, int size,Instant fromdDate, Instant toDate){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllCurrentLeaveTransactionByLeaveStatus(int page, int size,Instant fromdDate, Instant toDate,Integer leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        //return leaveRequestRepository.findAllByLeaveStatusAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(leaveStatus,fromdDate,toDate,loggedIn.getOrg(),pageble);
        return leaveRequestRepository.findAllByLeaveStatusAndEndDateTimeBetweenOrStartDateTimeAfterAndEndDateTimeBeforeAndOrganisationOrderByApplyDateDesc(leaveStatus,fromdDate,toDate,fromdDate,toDate,loggedIn.getOrg(),pageble);

    }

    public Page<LeaveRequest> findAllCurrentLeaveTransactionByDepartment(int page, int size,Instant fromdDate, Instant toDate, String departmentId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(departments,fromdDate,toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllCurrentLeaveTransactionByDepartmentAndLeaveStatus(int page, int size,Instant fromdDate, Instant toDate, String departmentId, Integer leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(departments,fromdDate,toDate,loggedIn.getOrg(),leaveStatus,pageble);
    }
    public Page<LeaveRequest> findAllCurrentLeaveTransactionByEmployeeType(int page, int size,Instant fromdDate, Instant toDate,String employeeTypeId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(employeType,fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllCurrentLeaveTransactionByEmployeeTypeAndLeaveStatus(int page, int size,Instant fromdDate, Instant toDate,String employeeTypeId,Integer leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndEndDateTimeBetweenAndOrganisationAndLeaveStatus(employeType,fromdDate, toDate,loggedIn.getOrg(),leaveStatus,pageble);
    }

    public Page<LeaveRequest> findAllCurrentLeaveTransactionByEmployeeTypeAndDepartment(int page, int size, Instant fromdDate, Instant toDate,String employeeTypeId, String departmentId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationOrderByApplyDateDesc(employeType,departments,fromdDate,toDate,loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllCurrentLeaveTransactionByEmployeeTypeAndDepartmentAndLeaveStatus(int page, int size, Instant fromdDate, Instant toDate,String employeeTypeId, String departmentId, int leaveStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        Departments departments = new Departments();
        departments.setId(departmentId);
        return leaveRequestRepository.findAllByEmployeEmployeTypeAndEmployeDepartmentsAndEndDateTimeBetweenAndOrganisationAndLeaveStatusOrderByApplyDateDesc(employeType,departments,fromdDate,toDate,loggedIn.getOrg() ,leaveStatus,pageble);
    }



    public Page<LeaveRequest> findAllRecievedRequestByLeaveStatus(String approverId,int leaveStatus ,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByApproverAndLeaveStatusAndOrganisationOrderByApplyDateDesc(new Employe(approverId), leaveStatus, loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllSentRequest(String employeeId, int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByEmployeAndOrganisationOrderByApplyDateDesc(new Employe(employeeId), loggedIn.getOrg(),pageble);
    }

    public Page<LeaveRequest> findAllSentRequestByLeaveStatus(String employeeId,int leaveTatus,int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return leaveRequestRepository.findAllByEmployeAndLeaveStatusAndOrganisationOrderByApplyDateDesc(new Employe(employeeId), leaveTatus,loggedIn.getOrg(),pageble);
    }

    public LeaveRequest applyLeave(LeaveRequest leaveRequest){

        leaveRequest.setOrganisation(leaveRequest.getOrganisation());
        leaveRequest.setEmploye(loggedIn.getUser().getEmployeId());
        leaveRequest.setCreateBy(loggedIn.getUser());
        leaveRequest.setCreateDate(Instant.now());
        leaveRequest.setApplyDate(Instant.now());
        leaveRequest.setLeaveStatus(1);
            Optional<Employe> manager  = clientProjectSubteamMemberService.getLeaveApprover();
            if(manager.isEmpty()){
                // @todo throw exception manager not found
            }else{
                // if requester and approver are not save
                if(!leaveRequest.getEmploye().getId().equalsIgnoreCase(manager.get().getId())){
                    leaveRequest.setApprover(manager.get());
                }
            }
            leaveRequest = leaveRequestRepository.save(leaveRequest);

        return  leaveRequest;
    }

    public LeaveRequest approveLeave(LeaveRequest leaveRequest){
        Optional<LeaveRequest> leaveRequestModel =  leaveRequestRepository.findById(leaveRequest.getId());
        if(!(leaveRequestModel.isEmpty()) ){
            if(!(leaveRequestModel.get().getEmploye().getId().equalsIgnoreCase(loggedIn.getUser().getEmployeId().getId()))) {
                LeaveRequest leaveRequestMole = leaveRequestModel.get();
                leaveRequestMole.setLeaveStatus(2);
                leaveRequestMole.setReviewerRemark(leaveRequest.getReviewerRemark());
                leaveRequestMole.setDateOfApproval(Instant.now());
                leaveRequestMole.setUpdateBy(loggedIn.getUser());
                leaveRequestMole.setLastUpdateDate(Instant.now());
                leaveRequest =leaveRequestRepository.save(leaveRequestMole);
            }
        }else{
            //@todo throw exception leave request not found
        }
        return leaveRequest;
    }

    public LeaveRequest rejectLeave(LeaveRequest request){
        Optional<LeaveRequest> leaveRequest =  leaveRequestRepository.findById(request.getId());
        if(!leaveRequest.isEmpty()){
            if(!(leaveRequest.get().getEmploye().getId().equalsIgnoreCase(loggedIn.getUser().getEmployeId().getId()))){
                LeaveRequest leaveRequestMole = leaveRequest.get();
                leaveRequestMole.setLeaveStatus(3);
                leaveRequestMole.setReviewerRemark(request.getReviewerRemark());
                leaveRequestMole.setDateOfApproval(Instant.now());
                leaveRequestMole.setUpdateBy(loggedIn.getUser());
                leaveRequestMole.setLastUpdateDate(Instant.now());
                request = leaveRequestRepository.save(leaveRequestMole);
            }
        }else{
            //@todo throw exception leave request not found
        }
        return  request;
    }


}
