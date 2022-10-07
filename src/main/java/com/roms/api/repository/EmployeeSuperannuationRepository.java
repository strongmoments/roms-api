package com.roms.api.repository;

import com.roms.api.model.EmployeeSuperannuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSuperannuationRepository extends JpaRepository<EmployeeSuperannuation, String> {
}