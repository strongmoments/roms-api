package com.roms.api.service;

import com.roms.api.controller.ResignationController;
import com.roms.api.model.*;
import com.roms.api.repository.EmployeeResignationRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeResignationService {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeResignationService.class);
    @Autowired
    private EmployeeResignationRepository employeeResignationRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private EmployeeManagerService employeeManagerService;

    @Autowired
    private EmployeService employeService;

    public Optional<EmployeeResignation> findById(String id){
      return employeeResignationRepository.findById(id);
    }


    public Page<EmployeeResignation> findAllRecievedRequestHistory( int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("dateOfApproval").descending());
        return employeeResignationRepository.findAllByApproverAndOrganisationAndStatusGreaterThan(loggedIn.getUser().getEmployeId(), loggedIn.getOrg(),1, pageble);
    }
    public Page<EmployeeResignation> findAllRecievedRequest( int page, int size){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return employeeResignationRepository.findAllByApproverAndOrganisation(loggedIn.getUser().getEmployeId(),loggedIn.getOrg(), pageble);
    }


    public Page<EmployeeResignation> findAllRecievedRequestByLeaveStatus( int page, int size, int resignationStatus){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return employeeResignationRepository.findAllByApproverAndStatusAndOrganisation(new Employe(loggedIn.getUser().getEmployeId().getId()), resignationStatus, loggedIn.getOrg(),pageble);
    }
    public List<EmployeeResignation> findAppliedResignation(){
        return employeeResignationRepository.findAllByEmployeeAndOrganisationOrderByApplyDateDesc(loggedIn.getUser().getEmployeId(),loggedIn.getOrg());
    }


    public Page<EmployeeResignation> findAllResignationTransaction(int page, int size,Instant fromdDate, Instant toDate){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        return employeeResignationRepository.findAllByApplyDateBetweenAndOrganisation(fromdDate, toDate,loggedIn.getOrg(),pageble);
    }
    public Page<EmployeeResignation> findAllResingTransactionByEmployeeTypeAndDepartmentAndStatus(int page, int size, Instant fromdDate, Instant toDate,String employeeTypeId, String departmentId, int status){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        Departments departments = new Departments();
        departments.setId(departmentId);
        return employeeResignationRepository.findAllByEmployeeEmployeTypeAndEmployeeDepartmentsAndApplyDateBetweenAndOrganisationAndStatus(employeType,departments,fromdDate, toDate,loggedIn.getOrg(),status,pageble);
    }

    public Page<EmployeeResignation> findAllResignTransactionByDepartmentAndLeaveStatus(int page, int size, Instant fromdDate, Instant toDate, String departmentId, int status){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        Departments departments = new Departments();
        departments.setId(departmentId);
        return employeeResignationRepository.findAllByEmployeeDepartmentsAndApplyDateBetweenAndOrganisationAndStatus(departments,fromdDate, toDate,loggedIn.getOrg(),status,pageble);
    }

    public Page<EmployeeResignation> findAllResigneTransactionByEmployeeTypeAndLeaveStatus(int page, int size, Instant fromdDate, Instant toDate, String employeeTypeId, int status){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        return employeeResignationRepository.findAllByEmployeeEmployeTypeAndApplyDateBetweenAndOrganisationAndStatus(employeType,fromdDate, toDate,loggedIn.getOrg(),status,pageble);
    }

    public Page<EmployeeResignation> findAllResigneTransactionByEmployeeTypeAndDepartment(int page, int size, Instant fromdDate, Instant toDate, String employeeTypeId,String departmentId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        Departments departments = new Departments();
        departments.setId(departmentId);
        return employeeResignationRepository.findAllByEmployeeEmployeTypeAndEmployeeDepartmentsAndApplyDateBetweenAndOrganisation(employeType,departments,fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<EmployeeResignation> findAllResignTransactionByDepartment(int page, int size, Instant fromdDate, Instant toDate, String departmentId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        Departments departments = new Departments();
        departments.setId(departmentId);
        return employeeResignationRepository.findAllByEmployeeDepartmentsAndApplyDateBetweenAndOrganisation(departments,fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<EmployeeResignation> findAllResigneTransactionByEmployeeType(int page, int size, Instant fromdDate, Instant toDate, String employeeTypeId){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());
        EmployeType employeType = new EmployeType();
        employeType.setId(employeeTypeId);
        return employeeResignationRepository.findAllByEmployeeEmployeTypeAndApplyDateBetweenAndOrganisation(employeType,fromdDate, toDate,loggedIn.getOrg(),pageble);
    }

    public Page<EmployeeResignation> findAllLeaveTransactionByLeaveStatus(int page, int size, Instant fromdDate, Instant toDate, Integer status){
        PageRequest pageble  = PageRequest.of(page, size, Sort.by("applyDate").descending());

        return employeeResignationRepository.findAllByApplyDateBetweenAndOrganisationAndStatus(fromdDate, toDate,loggedIn.getOrg(),status,pageble);
    }

    public Map<String,Object> checkResignationStatus() {
        Map<String,Object> response = new HashMap<>();
        Optional<EmployeeManagers> employeeManagers = employeeManagerService.getManager(loggedIn.getUser().getEmployeId().getId());
        if (employeeManagers.isEmpty()) {
            logger.info("manger not found");
            response.put("status", "error");
            response.put("error", "manager_not_found");
            return response;
        } else {

            Optional<EmployeeResignation> employeeResignation1 = employeeResignationRepository.findByEmployeeAndApproverAndOrganisationAndStatusIsNot(loggedIn.getUser().getEmployeId(), employeeManagers.get().getManagers(), loggedIn.getOrg(), 3);
            if (employeeResignation1.isPresent()) {
                response.put("status", "error");
                response.put("error", "already_resigned");
                logger.info("Already resigned");
                return response;
            }else{
                response.put("status","success");
                return response;
            }
        }

    }









    public EmployeeResignation resigne(EmployeeResignation employeeResignation, Map<String,Object> response){

        Optional<EmployeeManagers> employeeManagers = employeeManagerService.getManager(loggedIn.getUser().getEmployeId().getId());
        if(employeeManagers.isEmpty()){
            logger.info("manger not found");
            response.put("status","error");
            response.put("error","manager_not_found");
            return null;
        }else{

            Optional<EmployeeResignation> employeeResignation1 = employeeResignationRepository.findByEmployeeAndApproverAndOrganisationAndStatusIsNot(loggedIn.getUser().getEmployeId(),employeeManagers.get().getManagers(),loggedIn.getOrg(),3);
            if(employeeResignation1.isPresent()){
                response.put("status","error");
                response.put("error","already_resigned");
                logger.info("Already resigned");
                return null;
            }

          Employe manager =  employeeManagers.get().getManagers();
            employeeResignation.setApprover(manager);
            employeeResignation.setApplyDate(Instant.now());
            employeeResignation.setStatus(1);
            employeeResignation.setCreateBy(loggedIn.getUser());
            employeeResignation.setOrganisation(loggedIn.getOrg());
            employeeResignation.setCreateDate(Instant.now());
            employeeResignation =employeeResignationRepository.save(employeeResignation);

        }
        return employeeResignation;
    }

    public EmployeeResignation approveResignation(EmployeeResignation employeeResignation){
        String comment = employeeResignation.getComment();
        Optional<EmployeeResignation> employeeResignation1 = findById(employeeResignation.getId());
        if(employeeResignation1.isEmpty()){
            //@todo throw exception you dont have manager
            return null;
        }else{

            employeeResignation =  employeeResignation1.get();
            if(employeeResignation.getApprover().getId().equalsIgnoreCase(loggedIn.getUser().getEmployeId().getId())) {
                employeeResignation.setStatus(2);
                employeeResignation.setComment(comment);
                employeeResignation.setUpdateBy(loggedIn.getUser());
                employeeResignation.setLastUpdateDate(Instant.now());
                employeeResignation = employeeResignationRepository.save(employeeResignation);
                Employe employe = employeeResignation.getEmployee();
                employe.setEndDate(employeeResignation.getLastWorkingDate());
                employeService.update(employe);
                return employeeResignation;
            }

        }
        return  null;
    }

    public EmployeeResignation resectResignation(EmployeeResignation employeeResignation){
        String comment = employeeResignation.getComment();
        Optional<EmployeeResignation> employeeResignation1 = findById(employeeResignation.getId());
        if(employeeResignation1.isEmpty()){
            //@todo throw exception you dont have manager
            return null;
        }else{
            employeeResignation =  employeeResignation1.get();
            if(employeeResignation.getApprover().getId().equalsIgnoreCase(loggedIn.getUser().getEmployeId().getId())) {
                employeeResignation.setStatus(3);
                employeeResignation.setComment(comment);
                employeeResignation.setUpdateBy(loggedIn.getUser());
                employeeResignation.setLastUpdateDate(Instant.now());
                employeeResignation = employeeResignationRepository.save(employeeResignation);
                return employeeResignation;
            }
        }
        return null;
    }

}
