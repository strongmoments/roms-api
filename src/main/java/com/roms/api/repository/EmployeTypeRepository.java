package com.roms.api.repository;

import com.roms.api.model.EmployeType;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeTypeRepository extends JpaRepository<EmployeType,String> {

    List<EmployeType> findAllByOrganisation(Organisation organisation);
}
