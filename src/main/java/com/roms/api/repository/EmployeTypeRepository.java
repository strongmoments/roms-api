package com.roms.api.repository;

import com.roms.api.model.EmployeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeTypeRepository extends JpaRepository<EmployeType,String> {
}
