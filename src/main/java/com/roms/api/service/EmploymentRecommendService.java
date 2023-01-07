package com.roms.api.service;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeResourcedemand;
import com.roms.api.model.EmploymentRecommendation;
import com.roms.api.repository.EmploymentRecommendRepository;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EmploymentRecommendService {

    @Autowired
    private LoggedInUserDetails loggedIn;

    @Autowired
    private EmploymentRecommendRepository employmentRecommendRepository;

    public EmploymentRecommendation save(EmploymentRecommendation model){
        model.setCreateBy(loggedIn.getUser());
        model.setCreateDate(Instant.now());
        model.setOrganisation(loggedIn.getOrg());
        model.setInitiatedBy(loggedIn.getUser().getEmployeId());
        model.setRequestedDate(Instant.now());
        return employmentRecommendRepository.save(model);
    }

    public EmploymentRecommendation update(EmploymentRecommendation model){
        model.setLastUpdateDate(Instant.now());
        model.setUpdateBy(loggedIn.getUser());
        return employmentRecommendRepository.save(model);
    }
    public EmploymentRecommendation update2(EmploymentRecommendation model){
        model.setLastUpdateDate(Instant.now());
        return employmentRecommendRepository.save(model);
    }

    public List<EmploymentRecommendation> findAll(){
        return employmentRecommendRepository.findAll();

    }

    public List<EmploymentRecommendation> findAllByPendingDemands(){
        return employmentRecommendRepository.findAllByDemandIdxStatusAndOrganisationOrderByCreateDateDesc(0,loggedIn.getOrg());

    }

    public List<EmploymentRecommendation> findByResourceDemandId(String resourceDemandId){
        EmployeeResourcedemand rd = new EmployeeResourcedemand();
        rd.setId(resourceDemandId);
        return employmentRecommendRepository.findAllByDemandIdxAndOrganisationOrderByCreateDateDesc(rd,loggedIn.getOrg());
    }

    public List<EmploymentRecommendation> findByResourceDemandIdAndEmployeeId(String resourceDemandId,String employeeId){
        EmployeeResourcedemand rd = new EmployeeResourcedemand();
        rd.setId(resourceDemandId);
        Employe employee = new Employe();
        employee.setId(employeeId);
        return employmentRecommendRepository.findAllByStatusAndDemandIdxAndAndEmployeeIdx(1,rd,employee);
    }

    public boolean alreadyRequested(String resourceDemandId,String employeeId){
        List<EmploymentRecommendation>  allrecommend = findByResourceDemandIdAndEmployeeId( resourceDemandId, employeeId);
        if(!allrecommend.isEmpty()){
            return  true;
        }else {
            return false;
        }
    }

    public List<EmploymentRecommendation> findAllApprovedReport(){
        return employmentRecommendRepository.findAllByStatusAndOrganisationOrderByCreateByDesc(2,loggedIn.getOrg());
    }

    public Optional<EmploymentRecommendation> findById(String id){
        return employmentRecommendRepository.findById(id);
    }

    public List<EmploymentRecommendation> findAllPendingJobs(){
       return employmentRecommendRepository.findAllByJobFlagAndOrganisation(0,loggedIn.getOrg());
    }
}
