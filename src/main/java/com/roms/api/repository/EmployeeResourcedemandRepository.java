package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeResourcedemand;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeResourcedemandRepository extends JpaRepository<EmployeeResourcedemand,String> {

    public List<EmployeeResourcedemand>  findAllByOrganisationOrderByCreateDateDesc(Organisation org);

    public List<EmployeeResourcedemand>  findAllByOrganisationAndStatusOrderByCreateDateDesc(Organisation org,Integer status);

    public List<EmployeeResourcedemand>  findAllByOrganisationAndStatusAndDemandTypeOrderByCreateDateDesc(Organisation org,Integer status, Integer demandType);
    public List<EmployeeResourcedemand>  findAllByOrganisationAndHiringManagerOrCreateByEmployeIdAndStatusOrderByCreateDateDesc(Organisation org, Employe managerId,Employe createdBy,Integer status);

    public List<EmployeeResourcedemand>  findAllByDemandTypeAndOrganisationAndHiringManagerOrCreateByEmployeIdAndStatusOrderByCreateDateDesc(Integer demandType ,Organisation org, Employe managerId,Employe createdBy,Integer status);
}
