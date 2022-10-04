package com.roms.api.repository;

import com.roms.api.model.EmployeeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeAddressRepository extends JpaRepository<EmployeeAddress, String> {
}