package com.roms.api.repository;

import com.roms.api.model.EmployeeResourcedemand;
import com.roms.api.model.EmploymentRecommendation;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRecommendRepository extends JpaRepository<EmploymentRecommendation, String> {

  public List<EmploymentRecommendation> findAllByDemandIdxAndOrganisation(EmployeeResourcedemand rdemand, Organisation organisation);

  public List<EmploymentRecommendation> findAllByStatusAndOrganisation(Integer status,Organisation organisation);

}
