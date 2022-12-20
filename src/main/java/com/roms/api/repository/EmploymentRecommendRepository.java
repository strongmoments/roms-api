package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeResourcedemand;
import com.roms.api.model.EmploymentRecommendation;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRecommendRepository extends JpaRepository<EmploymentRecommendation, String> {

  public List<EmploymentRecommendation> findAllByDemandIdxAndOrganisationOrderByCreateDateDesc(EmployeeResourcedemand rdemand, Organisation organisation);

  public List<EmploymentRecommendation> findAllByDemandIdxStatusAndOrganisationOrderByCreateDateDesc(Integer demandStatus, Organisation organisation);

  public List<EmploymentRecommendation> findAllByStatusAndOrganisationOrderByCreateByDesc(Integer status,Organisation organisation);

  public List<EmploymentRecommendation> findAllByStatusAndDemandIdxAndAndEmployeeIdx(Integer status, EmployeeResourcedemand demand, Employe employe);

  public List<EmploymentRecommendation> findAllByJobFlagAndOrganisation(Integer jobStatus,Organisation org);



}
