package com.roms.api.repository;

import com.roms.api.model.Departments;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, String> {

    List<Departments>  findAllByOrganisation(Organisation org);
    Optional<Departments>  findByCodeAndOrganisation(String code, Organisation organisation);
}
