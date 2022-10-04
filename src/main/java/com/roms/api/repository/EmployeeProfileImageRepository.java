package com.roms.api.repository;

import com.roms.api.model.EmployeeProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeProfileImageRepository extends JpaRepository<EmployeeProfileImage, String> {
}
