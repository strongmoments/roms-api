package com.roms.api.repository;


import com.roms.api.model.Employe;
import com.roms.api.model.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe,String> {

    List<Employe> findAllByOrganisation(Organisation organisation);
}
