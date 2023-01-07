package com.roms.api.repository;

import com.roms.api.model.EmployeeAttendance;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, String> {
}
