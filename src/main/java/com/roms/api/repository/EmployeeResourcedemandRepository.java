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

    public List<EmployeeResourcedemand>  findAllByOrganisationAndAndStatusOrderByCreateDateDesc(Organisation org,Integer status);
    public List<EmployeeResourcedemand>  findAllByOrganisationAndHiringManagerOrCreateByEmployeId(Organisation org, Employe managerId,Employe createdBy);
}
