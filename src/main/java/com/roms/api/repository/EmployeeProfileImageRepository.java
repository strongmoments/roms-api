package com.roms.api.repository;

import com.roms.api.model.Employe;
import com.roms.api.model.EmployeeProfileImage;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProfileImageRepository extends JpaRepository<EmployeeProfileImage, String> {

    List<EmployeeProfileImage>  findAllByEmployeAndOrganisation(Employe employe, Organisation organisation);
}
